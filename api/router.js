const app = require('express')()
const api = require('./controllers/api')

app.use('/api', api)

module.exports = app