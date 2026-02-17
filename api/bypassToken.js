Java.perform(() => {

    let GenerateToken = Java.use('hc.b4sh0xf.talk.GenerateToken')

    GenerateToken['v_chk_01'].implementation = function(a) {
        console.log('[*] bypassing token verification...')
        return false
    }

})