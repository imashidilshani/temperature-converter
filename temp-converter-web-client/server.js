const express = require('express');
const path = require('path');
const app = express();
const PORT = process.env.PORT || 3000;


const BACKEND_URL = process.env.BACKEND_URL || 'http://temp-api-container:8080';

// API Key for Security Authentication
const API_KEY = process.env.API_KEY || 'my-secret-temp-key-123';

app.use(express.json());

// Serve Static Files
app.use(express.static(path.join(__dirname)));

// Root Route to serve index.html directly
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
});

// Helper function to safely parse response
async function handleResponse(response, res) {
    const contentType = response.headers.get('content-type');
    
    if (contentType && contentType.includes('application/json')) {
        const data = await response.json();
        return res.status(response.status).json(data);
    } else {
        const textMessage = await response.text();
        return res.status(response.status).json({ 
            error: `Backend Error (${response.status})`, 
            details: textMessage 
        });
    }
}

// 1. Express Proxy for Conversion
app.post('/api/convert', async (req, res) => {
    try {
        const { value, unit } = req.body;
        const backendUrl = `${BACKEND_URL}/api/temperatures/convert?value=${value}&unit=${encodeURIComponent(unit)}`;

        console.log(`[Proxy] Forwarding POST to: ${backendUrl}`);

        const response = await fetch(backendUrl, {
            method: 'POST',
            headers: {
                'X-API-KEY': API_KEY,
                'Content-Type': 'application/json'
            }
        });

        await handleResponse(response, res);

    } catch (error) {
        console.error('[Proxy Error - Convert]:', error.message);
        res.status(500).json({ 
            error: 'Proxy Connection Failure', 
            details: `Cannot reach Spring Boot Backend at ${BACKEND_URL}. Exception: ${error.message}` 
        });
    }
});

// 2. Express Proxy for History Logs
app.get('/api/history', async (req, res) => {
    try {
        const backendUrl = `${BACKEND_URL}/api/temperatures/logs`;
        console.log(`[Proxy] Forwarding GET to: ${backendUrl}`);

        const response = await fetch(backendUrl, {
            method: 'GET',
            headers: {
                'X-API-KEY': API_KEY
            }
        });

        await handleResponse(response, res);

    } catch (error) {
        console.error('[Proxy Error - History]:', error.message);
        res.status(500).json({ error: 'Proxy Connection Failure', details: error.message });
    }
});

// 3. Express Proxy for Filtering Logs 
app.get('/api/filter', async (req, res) => {
    try {
        const unit = req.query.unit;

       
        if (!unit || unit === 'All') {
            const historyUrl = `${BACKEND_URL}/api/temperatures/logs`;
            console.log(`[Proxy] Redirecting filter request to all history: ${historyUrl}`);
            const response = await fetch(historyUrl, {
                method: 'GET',
                headers: { 'X-API-KEY': API_KEY }
            });
            return await handleResponse(response, res);
        }

        const backendUrl = `${BACKEND_URL}/api/temperatures/filter?unit=${encodeURIComponent(unit)}`;
        console.log(`[Proxy] Forwarding GET to: ${backendUrl}`);

        const response = await fetch(backendUrl, {
            method: 'GET',
            headers: {
                'X-API-KEY': API_KEY
            }
        });

        await handleResponse(response, res);

    } catch (error) {
        console.error('[Proxy Error - Filter]:', error.message);
        res.status(500).json({ error: 'Proxy Connection Failure', details: error.message });
    }
});

// 4. Express Proxy for Safety Check
app.get('/api/safety-check', async (req, res) => {
    try {
        const { value, unit } = req.query;
        const backendUrl = `${BACKEND_URL}/api/temperatures/safety-check?value=${value}&unit=${encodeURIComponent(unit)}`;
        console.log(`[Proxy] Forwarding GET to: ${backendUrl}`);

        const response = await fetch(backendUrl, {
            method: 'GET',
            headers: {
                'X-API-KEY': API_KEY
            }
        });

        const textMessage = await response.text(); 
        res.status(response.status).send(textMessage);

    } catch (error) {
        console.error('[Proxy Error - Safety Check]:', error.message);
        res.status(500).send(`Error connecting to safety check service: ${error.message}`);
    }
});

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
    console.log(`Targeting Backend URL: ${BACKEND_URL}`);
});