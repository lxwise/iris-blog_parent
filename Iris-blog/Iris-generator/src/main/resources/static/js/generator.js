$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/generator/list',
        mtype: "POST",  // 修改请求方式为POST
        datatype: "json",
        colModel: [
            { label: '表名', name: 'tableName', width: 100, key: true },
            { label: 'Engine', name: 'engine', width: 70},
            { label: '表备注', name: 'tableComment', width: 100 },
            { label: '创建时间', name: 'createTime', width: 100 }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10,30,50,100,200],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.records",
            page: "data.page",
            total: "data.totalPages",
            records: "data.total"
        },
        prmNames: {
            page: "page",
            rows: "pageSize",
            order: "order"
        },
        ajaxGridOptions: { contentType: "application/json" }, // 设置内容类型为JSON
        serializeGridData: function (postData) {
            if (postData.tableName != null) {
                 postData._search = true;
            }
            // 将postData序列化为JSON字符串
            return JSON.stringify(postData);
        },
        gridComplete: function () {
            // 隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        }
    });
});


var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            tableName: null
        }
    },
    methods: {
        query: function () {
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'tableName': vm.q.tableName},
                page:1
            }).trigger("reloadGrid");
        },
        generator: function() {
            var tableNames = getSelectedRows();
            if(tableNames == null){
                return ;
            }
            location.href = "sys/generator/code?tables=" + tableNames.join();
        }
    }
});

