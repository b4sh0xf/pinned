const mongoose = require('mongoose')

const mongoURL = process.env.MONGO_URL || 'mongodb://localhost:27017/pinned_db';

mongoose.connect(mongoURL)
.then(() => {console.log('[*] mongo online')})
.catch((err) => {console.log(err)})

// docker run --name pinned -p 27017:27017 -d mongo:latest