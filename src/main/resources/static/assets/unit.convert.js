function convertToWanYuan(datas) {
    var result =[];
    for(var i=0;i<datas.length;++i){
        result.push(+(datas[i]/10000).toFixed(6));
    }
    return result;
}