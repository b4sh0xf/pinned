package hc.b4sh0xf.talk

object ApiConfig {
    private const val DOMAIN = "pinned.hc"
    private var serverIp: String = ""
    private var serverPort: String = "80"

    fun setServer(ip: String, port: String) {
        serverIp = ip
        serverPort = port
        RetroFitClient.resetInstance()
    }

    fun getBaseUrl(): String {
        return if (serverIp.isNotEmpty()) {
            "http://$serverIp:$serverPort/"
        } else {
            "http://$DOMAIN/"
        }
    }

    fun getDomain(): String {
        return DOMAIN
    }

    fun isConfigured(): Boolean {
        return serverIp.isNotEmpty()
    }
}
