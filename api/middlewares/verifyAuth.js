function verifyAuth(req, res, next) {
    
    const header = req.get("Authorization")

    if (!header) {
        return res.status(403).json({sucess: false, error: "missing Authorization header"})
    }

    if(header.split(' ')[1].length != 12) {
        return res.status(403).json({sucess: false, error: "only authenticated users can access this route."})
    }

    next()

}

module.exports = verifyAuth