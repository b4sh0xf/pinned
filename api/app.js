const express = require('express')
const app = express()
const port = 3000
const router = require('./router')
require('./database')

app.use(express.json())
app.use('/', router)

app.listen(port, () => {
    console.log(`[*] server running at http://localhost:${port}`)
})