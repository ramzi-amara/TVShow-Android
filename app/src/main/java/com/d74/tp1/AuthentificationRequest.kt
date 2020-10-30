package layout

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class AuthentificationRequest(
    method: Int,
    url: String?,
    listener: Response.Listener<String>?,
    errorListener: Response.ErrorListener?
    ) : StringRequest(method, url, listener, errorListener) {
        private lateinit var username: String
        private lateinit var password: String
        constructor(
            method: Int,
            url: String?,
            username: String,
            password: String,
            listener: Response.Listener<String>?,
            errorListener: Response.ErrorListener?
        ) : this(method, url, listener, errorListener) {
            this.username = username
            this.password = password
        }
        override fun getParams(): MutableMap<String, String> {
            val body: MutableMap<String, String> = HashMap()
            body["grant_type"] = "password"
            body["username"] = this.username
            body["password"] = this.password
            return body
        }
        override fun getBodyContentType(): String? {
            return "application/x-www-form-urlencoded; charset=UTF-8"
        }

    }