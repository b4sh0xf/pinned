const app = require('express')()
const User = require('../../models/User')
const verifyAuth = require('../../middlewares/verifyAuth')
const verifyMobile = require('../../middlewares/verifyMobile')

app.get('/login/:username', verifyAuth, verifyMobile, async (req, res) => {

    const { username } = req.params

    const fetchData = await User.findOne({ username }, 'lastLogin -_id')
    
    try {

        if (fetchData) {
            return res.status(200).json({
                sucess: true,
                fetch: fetchData
            })
        } else {
            return res.status(404).json({sucess: false, error: 'username not found'})
        }

    } catch (err) {
        return res.status(500).json({sucess: false, error: 'cannot fetch user data'})
    }

})

module.exports = app