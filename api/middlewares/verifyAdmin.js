const User = require('../models/User')

async function verifyAdmin(req, res) {

    const timestamp = req.get('Authorization')

    if (!timestamp) {
        return res.status(403).json({sucess: false, error: 'missing Authorization header'})
    }

    const adminTimestamp = await User.findOne({ username: 'admin' }, 'lastLogin -_id')
    
    try {
        if (timestamp.split(' ')[1] == adminTimestamp['lastLogin']) {
            next()
        } else {
            return res.status(403).json({sucess: false, error: 'admin access only.'})
        }
    } catch (err) {
        return res.status(500).json({sucess: false, error: 'error to fetch flag.'})
    }

}

module.exports = verifyAdmin