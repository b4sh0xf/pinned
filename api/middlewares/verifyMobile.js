function verifyMobile(req, res, next) {

    const header = req.get("User-Agent")

    if (!header.includes("okhttp")) {
        return res.status(401).json({sucess: false, error: 'please use a phone.'})
    }
    next()

}

module.exports = verifyMobile