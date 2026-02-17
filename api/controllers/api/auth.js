const app = require('express')()
const User = require('../../models/User')
const verifyMobile = require('../../middlewares/verifyMobile')

app.post('/register', verifyMobile, async (req, res) => {

    const { username, password } = req.body

    if (!username || !password) {
        return res.status(400).json({sucess: false, error: 'username and password cannot be null.'})
    }

    if (typeof username !== 'string' && typeof password !== 'string') {
        return res.status(400).json({sucess: false, error: 'all fields should be strings.'})
    }

    const userExists = await User.findOne({ username })
    let timestamp = new Date().toISOString().split('-')[0] + new Date().toISOString().split('-')[1] + new Date().toISOString().split('-')[2].split(':')[0].split('T')[0] + new Date().toISOString().split('-')[2].split(':')[0].split('T')[1] + new Date().toISOString().split('-')[2].split(':')[1]

    try {
        if (userExists) {
            return res.status(403).json({sucess: false, error: 'user already exists.'})
        }
        const newUser = new User({ username, password, timestamp })
        return res.status(201).json({sucess: true, msg: 'user created.', Authorization: `Bearer ${timestamp}`})
    } catch (err) {
        return res.status(500).json({sucess: false, error: 'error to create user.'})
    }

})

app.post('/login', verifyMobile, async (req, res) => {

    const { username, password } = req.body

    if (!username || !password) {
        return res.status(400).json({sucess: false, error: 'username or password cannot be null.'})
    }

    if (typeof username !== 'string' && typeof password !== 'string') {
        return res.status(400).json({sucess: false, error: 'all fields should be strings.'})
    }


    try {
        const userMatch = await User.findOne({ username })

        if (!userMatch) {
            return res.status(400).json({sucess: false, error: 'invalid username or password'})
        }

        const passwordMatch = await User.findOne({ password })

        if (!passwordMatch) {
            return res.status(400).json({sucess: false, error: 'invalid username or password'})
        }

        const lastLogin = new Date().toISOString().split('-')[0] + new Date().toISOString().split('-')[1] + new Date().toISOString().split('-')[2].split(':')[0].split('T')[0] + new Date().toISOString().split('-')[2].split(':')[0].split('T')[1] + new Date().toISOString().split('-')[2].split(':')[1]
        userMatch.lastLogin = lastLogin
        await userMatch.save()
        return res.status(200).set({'Authorization':`Bearer ${lastLogin}`}).json({
            sucess: true, 
            msg: 'login sucessful',
            lastLogin: lastLogin
        })
    } catch (err) {
        return res.status(500).json({sucess: false, error: 'error to login.'})
    }

})

module.exports = app