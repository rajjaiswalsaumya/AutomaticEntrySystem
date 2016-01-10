groups {
    allmincss {
        css(minimize: false, "/css/bootstrap.min.css")
    }
    allcss {
        css(minimize: false, "/css/bootstrap.min.css")
    }
    allminjs {
        js(minimize: true, "/webjars/bootstrap/3.3.6/js/bootstrap.min.js")
        js(minimize: true, "/webjars/jquery/2.2.0/jquery.min.js")
        js(minimize: true, "/webjars/jquery-ui/1.11.4/jquery-ui.min.js")
    }
    alljs {
        js(minimize: false, "/webjars/bootstrap/3.3.6/js/bootstrap.js")
        js(minimize: false, "/webjars/jquery/2.2.0/jquery.js")
        js(minimize: false, "/webjars/jquery-ui/1.11.4/jquery-ui.js")
    }
    allmin {
        allmincss()
        allminjs()
    }
    all {
        allcss()
        alljs()
    }
}