$(document).ready(function() {
   
   var baseUrl = "http://localhost:8080/Rest_JAX-RS/api/quotes";
   
    $("#getRandomQuote").on("click", function(e) {
        e.preventDefault()
        getQuote(baseUrl, todaysQuote);
    });
    
    $("#getQuote").on("click", function(e) {
        e.preventDefault()
        
        var regex = /^[0-9]+$/;
        var val = $("#textfield").val();
        
        if (val.match(regex)) {
            getQuote(baseUrl + "/" + $("#textfield").val(), appendValue);
            $("#quoteId").val($("#textfield").val())
        } else  {
            getQuote(baseUrl, appendValue);
            $("#quoteId").val("")
        }
    });
    
    $("#saveQuote").on("click", function(e) {
        e.preventDefault()
        
        if ($("#quoteId").val() === "") {
            saveQuote($("#textfield").val())
            $("#textfield").val("")
        } else {
            updateQuote($("#quoteId").val(), $("#textfield").val())
            $("#textfield").val("")
            $("#quoteId").val("")
        }
    });
    
    $("#deleteQuote").on("click", function(e) {
        e.preventDefault()
        deleteQuote($("#textfield").val())
        $("#textfield").val("")
    });
    
    
    function getQuote(url, callback) {
        
        $.ajax({
            url: url,
            success: function(data, response, obj) {
                callback(data.quote)
            },
            error: function(data, response, obj) {
                setMsg("danger", data.responseJSON.message)
            }
        })
    }
    
    function saveQuote(val) {
        
        $.ajax({
            method: "POST",
            url: baseUrl,
            contentType: 'application/json',
            data: JSON.stringify({quote: val}),
            success: function(data, response, obj) {
                setMsg("success", "New quote added with the id: " + data.id + " and text: " + data.quote)
            },
            error: function(data, response, obj) {
                setMsg("danger", data.responseJSON.message)
            }
        })
    }
    
    function deleteQuote(id) {
        
        $.ajax({
            method: "DELETE",
            url: baseUrl + "/" + id,
            contentType: 'application/json',
            success: function(data, response, obj) {
                setMsg("success", "Quote deleted: " + data.quote)
            },
            error: function(data, response, obj) {
                setMsg("danger", data.responseJSON.message)
            }
        })
    }
    
    
    function updateQuote(id, val) {
        
        $.ajax({
            method: "PUT",
            url: baseUrl + "/" + id,
            contentType: 'application/json',
            data: JSON.stringify({quote: val}),
            success: function(data, response, obj) {
                setMsg("success", "Quote updated with the id: " + data.id + " and text: " + data.quote)
            },
            error: function(data, response, obj) {
                setMsg("danger", data.responseJSON.message)
            }
        })
        
    }
    
    
    function todaysQuote(val) {
        $("#quoteOfTheDay").text(val)
    }
    
    function appendValue(val) {
        $("#textfield").val(val);
    }
    
    
    function setMsg(type, msg) {
        
        var msgs = $(".alert");
        var id = msgs.length;
        
        $("#msg").append('<div class="alert alert-' + type + '" id="alert_' + id + '">' + msg + '</div>')
            
        setTimeout(function() {
            $("#alert_" + id).remove();
        }, 6000); 
    }   
});