package demo.wsc.beta.exceptions

class WSCExceptionServiceDown : Exception() {

    override var message = "Service Down"
    override fun toString(): String {
        return "WSCExceptionServiceDown{" +
                "message='" + message + '\'' +
                '}'
    }

}