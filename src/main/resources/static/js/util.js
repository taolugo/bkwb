function getData(url,type,callback){
    $.ajax({
        type:type,
        url:"http://localhost:8081" + url,
        dataType:"json",
        success:function(data){
            callback(data)
        },
        error:function(jqXHR){
            console.log("Error: "+jqXHR.status);
        }
    });
}

function getData2(url,type,callback,dataType){
    if (dataType=="" || dataType==undefined) dataType="json";
    $.ajax({
        type:type,
        url:"http://localhost:8081" + url,
        dataType:dataType,
        success:function(data){
            callback(data);
        },
        error:function(jqXHR){

            console.log("Error: "+jqXHR.status);
        }
    });
}