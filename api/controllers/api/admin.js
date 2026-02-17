const app = require('express')()
const verifyAdmin = require('../../middlewares/verifyAdmin')
const verifyMobile = require('../../middlewares/verifyMobile')

app.get('/flag', verifyAdmin, verifyMobile, (req, res) => {

    return res.status(200).json({sucess: true, flag: 'hackingclub{m4n1pul4t1ng_runt1m3_4nd_fry1ng_4p1!}'})

})

module.exports = app