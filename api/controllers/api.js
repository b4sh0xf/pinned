const app = require('express')()
const auth = require('./api/auth')
const last = require('./api/last')
const admin = require('./api/admin')

app.use('/auth', auth)
app.use('/last', last)
app.use('/admin', admin)

module.exports = app