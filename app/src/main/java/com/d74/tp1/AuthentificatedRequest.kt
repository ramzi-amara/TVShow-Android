package layout

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class AuthentificatedRequest (
    method: Int,
    url: String?,
    listener: Response.Listener<String>?,
    errorListener: Response.ErrorListener?
    ) : StringRequest(method, url, listener, errorListener) {
        private lateinit var bearerToken: String
        constructor(
            method: Int,
            url: String?,
        bearerToken: String,
        listener: Response.Listener<String>?,
        errorListener: Response.ErrorListener?
        ) : this(method, url, listener, errorListener) {
            this.bearerToken = bearerToken
        }
        override fun getHeaders(): MutableMap<String, String> {
            val headers: MutableMap<String, String> = HashMap()
            headers["Authorization"] = "bearer ${this.bearerToken}"
            return headers
        }
}
