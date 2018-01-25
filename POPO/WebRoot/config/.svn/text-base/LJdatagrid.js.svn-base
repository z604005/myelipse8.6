
var DBGrid_Chang_Color = "#cbe5ff"; //DBGrid修改後的底色要用小寫
var ischecked = false;
function createHttpRequest() {
    if (window.ActiveXObject) {
        try {
            return new ActiveXObject("Microsoft.XMLHTTP");
        }
        catch (e2) {
            return null;
        }
    } else {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        } else {
            return null;
        }
    }
}
function DivSetVisible(state, divform) {
    var INPUT = document.getElementById(divform);
    var LOADING = document.getElementById("detailloading");
    var DivRef = document.getElementById("detailDivAttn");
    var IfrRef = document.getElementById("detailDivShim");
    if (state) {
        x = INPUT.offsetLeft + (INPUT.offsetWidth / 2);
        y = INPUT.offsetTop + (INPUT.offsetHeight / 2);
        LOADING.style.top = y - 5;
        LOADING.style.left = x - 5;
        LOADING.style.display = "block";
        LOADING.innerHTML = "<img src=\"config/loading.gif\">";
        DivRef.style.width = INPUT.offsetWidth;
        DivRef.style.height = INPUT.offsetHeight;
        DivRef.style.top = INPUT.offsetTop;
        DivRef.style.left = INPUT.offsetLeft;
        DivRef.style.filter = "alpha(opacity='50')";
        DivRef.style.zIndex = LOADING.style.zIndex - 1;
        DivRef.style.display = "block";
        IfrRef.style.width = DivRef.offsetWidth;
        IfrRef.style.height = DivRef.offsetHeight;
        IfrRef.style.top = DivRef.offsetTop;
        IfrRef.style.left = DivRef.offsetLeft;
        IfrRef.style.filter = "alpha(opacity='50')";
        IfrRef.style.zIndex = DivRef.style.zIndex - 1;
        IfrRef.style.display = "block";
    } else {
        LOADING.style.display = "none";
        DivRef.style.display = "none";
        IfrRef.style.display = "none";
    }
}
function handleAjaxResult(req, method, divform, url) {
    return function () {
        if (req.readyState == 4) {     
     //DivSetVisible(false,divform);
            if (req.status == 200) {
                eval(method + "(url,req.responseText);");
            } else {
                alert("\u57f7\u884c\u932f\u8aa4!" + req.status);
            }
        }
    };
}
function Detail_save(url, content, actionCode, divform, handleMethod) {
	//DivSetVisible(true,divform);
    ajax = createHttpRequest();
    ajax.onreadystatechange = handleAjaxResult(ajax, handleMethod, divform, url);
    ajax.open("POST", url);
    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    ajax.setRequestHeader("If-Modified-Since","0");
    ajax.send("reqCode=Detail_save&content=" + encodeURIComponent(content) + "&actionCode=" + actionCode);
}
function Detail_cancel(url, content, divform, handleMethod, actionCode, rownum) {
	//DivSetVisible(true,divform);
    ajax = createHttpRequest();
    ajax.onreadystatechange = handleAjaxResult(ajax, handleMethod, divform, rownum);
    ajax.open("POST", url);
    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    ajax.setRequestHeader("If-Modified-Since","0");
    ajax.send("reqCode=Detail_cancel&content=" + encodeURIComponent(content) + "&actionCode=" + actionCode);
}
function CloseDetailButton() {
    var DetailButton1 = document.getElementById("DetailButton");
    if (DetailButton1 != null) {
        DetailButton1.style.display = "none";
    }
}
function Detail_select_Head(url, divform, mode, pagenum, keyid) {
    var INPUT = document.getElementById("HEADDIV");
    var DivRef = document.getElementById("headDivAttn");
    var IfrRef = document.getElementById("headDivShim");
    DivRef.style.width = INPUT.offsetWidth;
    DivRef.style.height = INPUT.offsetHeight;
    DivRef.style.top = INPUT.offsetTop;
    DivRef.style.left = INPUT.offsetLeft - 5;
    DivRef.style.zIndex = 100;
    DivRef.style.filter = "alpha(opacity='50')";
    DivRef.style.display = "block";
    IfrRef.style.width = DivRef.offsetWidth + 5;
    IfrRef.style.height = DivRef.offsetHeight;
    IfrRef.style.top = DivRef.offsetTop;
    IfrRef.style.left = DivRef.offsetLeft;
    IfrRef.style.zIndex = DivRef.style.zIndex - 1;
    IfrRef.style.filter = "alpha(opacity='50')";
    IfrRef.style.display = "block";
    document.getElementById("DBGRIDDetail").style.visibility = "visible";
    ischecked = true;
    Detail_select(url, divform, mode, pagenum, keyid);
}
function Detail_select_Head_Leave(url, divform, mode, pagenum, keyid) {
    if (checkExit(1, 0)) {
        document.getElementById("headDivAttn").style.display = "none";
        document.getElementById("headDivShim").style.display = "none";
        document.getElementById("DBGRIDDetail").style.visibility = "hidden";
    }
}
/**
參數值 
url:程式名稱
divform:要處裡的DIV的id名稱
mode:定義新增模式(new) or 維護
pagenum:第幾頁資料(0 for 不分頁)
keyid:表頭key的id內容 (分隔符號{;})
**/
function Detail_select(url, divform, mode, pagenum, keyid) {
    var keyvaluearray;
    var keyvalue = "";
    if (keyid != "") {
        keyvaluearray = keyid.split("{;}");
        for (i = 0; i < keyvaluearray.length; i = i + 1) {
            if (keyvaluearray[i] != "") {
                a = document.getElementById(keyvaluearray[i]);
                keyvalue += a.value + "{;}";
            }
        }
    }
    var remsg = "";
    DivSetVisible(true, divform);
    ajax = createHttpRequest();
    var DIVSHOW = document.getElementById(divform);
    ajax.onreadystatechange = function () {
        if (ajax.readyState == 4) {
            DivSetVisible(false, divform);
            if (ajax.status == 200) {	
			//alert(ajax.responseText);
                DIVSHOW.innerHTML = ajax.responseText;
            } else {
                DIVSHOW.innerHTML = alert("\u57f7\u884c\u932f\u8aa4!" + ajax.status);
            }
        }
    };
    ajax.open("POST", url);
    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    ajax.setRequestHeader("If-Modified-Since","0");
    if (DIVSHOW.innerHTML == "") {
        ajax.send("reqCode=Detail_select&mode=" + mode + "&pagenum=" + pagenum + "&keyvalue=" + encodeURIComponent(keyvalue) + "&keyid=" + keyid);
    } else {
        if (!ischecked) {
            if (checkExit(pagenum, 0)) {
                ischecked = false;
                ajax.send("reqCode=Detail_select&mode=" + mode + "&pagenum=" + pagenum + "&keyvalue=" + encodeURIComponent(keyvalue) + "&keyid=" + keyid);
            } else {
                ischecked = false;
                DivSetVisible(false, divform);
            }
        } else {
            ischecked = false;
            ajax.send("reqCode=Detail_select&mode=" + mode + "&pagenum=" + pagenum + "&keyvalue=" + encodeURIComponent(keyvalue) + "&keyid=" + keyid);
        }
    }
}
function LovLeave() {
    document.all.BA_DATAGRID_LOV_DIV.style.display = "none";
    document.getElementById("detailDivShim").style.display = "none";
    document.all.DBGRIDDetail.style.visibility = "visible";
}
function Lov_select(sql, sqlother, cfield_name, sqlkeyCmd, sqlkeyid, dg_keyvalue_cname, dg_keyvalue_cname2, lovfield_id, lovfield_name, divform, pagenum, rowindex) {
    var DBGRIDSHOW = document.getElementById("DBGRIDDetail");
    var remsg = "";
    ajax = createHttpRequest();
    var DIVSHOW = document.getElementById(divform);
    ajax.onreadystatechange = function () {
        if (ajax.readyState == 4) {
            DivSetVisible(false, divform);
            if (ajax.status == 200) {
                DIVSHOW.innerHTML = ajax.responseText;
                DIVSHOW.style.width = DBGRIDSHOW.offsetWidth / 7 * 6;
                DIVSHOW.style.height = 480;
                DIVSHOW.style.zIndex = 101;
                DIVSHOW.style.top = DBGRIDSHOW.offsetTop;
                DIVSHOW.style.left = DBGRIDSHOW.offsetLeft + 20;
                var IfrRef = document.getElementById("detailDivShim");
                IfrRef.style.width = DBGRIDSHOW.offsetWidth;
                IfrRef.style.height = DBGRIDSHOW.offsetHeight;
                IfrRef.style.top = DBGRIDSHOW.offsetTop;
                IfrRef.style.left = DBGRIDSHOW.offsetLeft;
                IfrRef.style.filter = "alpha(opacity='0')";
                IfrRef.style.zIndex = DIVSHOW.style.zIndex - 1;
                IfrRef.style.display = "block"; 
				
				//DBGRIDSHOW.style.visibility= "hidden";
                DIVSHOW.style.display = "block";
            } else {
                DIVSHOW.innerHTML = alert("\u57f7\u884c\u932f\u8aa4!" + ajax.status);
            }
        }
    };
    ajax.open("POST", "DATAGRID_LOV.do");
    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    ajax.setRequestHeader("If-Modified-Since","0");
    ajax.send("reqCode=Lov_select&sql=" + encodeURIComponent(sql) + "&sqlother=" + encodeURIComponent(sqlother) + "&cfield_name=" + encodeURIComponent(cfield_name) + "&sqlkeyCmd=" + encodeURIComponent(sqlkeyCmd) + "&sqlkeyid=" + sqlkeyid + "&dg_keyvalue_cname=" + encodeURIComponent(dg_keyvalue_cname) + "&dg_keyvalue_cname2=" + encodeURIComponent(dg_keyvalue_cname2) + "&lovfield_id=" + lovfield_id + "&lovfield_name=" + encodeURIComponent(lovfield_name) + "&pagenum=" + pagenum + "&rowindex=" + rowindex);
}
function Lov_DO_select(sql, sqlother, cfield_name, sqlkeyid, dg_keyvalue_cname, lovfield_id, lovfield_name, divform, pagenum, rowindex) {
    var NameArr; //放欄位名稱用
    var colcount;
    var strTmp;
    var strValue;
    NameArr = dg_keyvalue_cname.split("{;}");
    colcount = document.all.DBTableDetail.rows(rowindex).cells.length;
    strValue = "";
    for (var j = 0; j < NameArr.length; j++) {
        for (var i = 0; i < colcount; i++) {
            strTmp = document.all.DBTableDetail.rows(0).cells(i).innerText;
            if (strTmp == NameArr[j] || strTmp == "\u25ce" + NameArr[j]) {
                if (document.all["column" + rowindex + "-" + i].value == "") {
                    strValue += " {;}";
                } else {
                    strValue += document.all["column" + rowindex + "-" + i].value + " {;}";
                }
                i = colcount;
            }
        }
    }
    Lov_select(sql, sqlother, cfield_name, " ", sqlkeyid, strValue, " ", lovfield_id, lovfield_name, divform, pagenum, rowindex);
}
function setColWidth(num) {
		//當使用者按下標題列時
		//將欄位變寬
    var colcount;
    var rowcount;
    var colType;
    var colWidth;
		//取得欄數
    colcount = document.all.DBTableDetail.rows(1).cells.length;
		//取得列數
    rowcount = document.all.DBTableDetail.rows.length;
		//取得該欄的型態
    colType = document.all.DBTableDetail.rows(1).cells(num).innerText.substring(0, 1);
    switch (colType) {
      case "B":  //還原按鈕,刪除
        break;
      default: 
				//取得該欄的預設欄寬
        colWidth = document.all.DBTableDetail.rows(1).cells(num).innerText.substring(3, document.all.DBTableDetail.rows(1).cells(num).innerText.length);
        if (document.all.DBTableDetail.cells(num).style.width == colWidth) {
					//要變大
            for (var i = 0; i < colcount; i++) {
						//取得要處理的欄位資訊
                colWidth = document.all.DBTableDetail.rows(1).cells(i).innerText.substring(3, document.all.DBTableDetail.rows(1).cells(i).innerText.length);
                colType = document.all.DBTableDetail.rows(1).cells(i).innerText.substring(0, 3);
						//隱藏欄位不用處理
                if (colType.substring(2, 3) != "H") {
							//變更欄寬
                    document.all.DBTableDetail.cells(i).style.width = colWidth;
							//改變TEXTBOX的寬度
                    for (var j = 0; j < rowcount; j++) {
                        if (i == num) {
									//將自已變寬
                            document.all.DBTableDetail.rows(j).cells(i).style.display = "inline";
                            if (j > 1) {
                                document.all["column" + j + "-" + i].style.width = "760px";
                            }
                        } else {
									//將其他欄位隱藏
                            switch (colType.substring(1, 2)) {
                              case "B":  //還原按鈕不用隱藏,delete不用隱藏
                              case "K":  //主鍵不用隱藏
                                break;
                              default:
                                document.all.DBTableDetail.rows(j).cells(i).style.display = "none";
                            }
                            if (j > 1) {
                                document.all["column" + j + "-" + i].style.width = colWidth;
                            }
                        }
                    }
                }
            }
            document.all.DBTableDetail.cells(num).style.width = "760px";
        } else {
					//還原欄寬
            for (var i = 0; i < colcount; i++) {
                colWidth = document.all.DBTableDetail.rows(1).cells(i).innerText.substring(3, document.all.DBTableDetail.rows(1).cells(i).innerText.length);
                colType = document.all.DBTableDetail.rows(1).cells(i).innerText.substring(2, 3);
                if (colType != "H") {
                    document.all.DBTableDetail.cells(i).style.width = colWidth;
                    for (var j = 0; j < rowcount; j++) {
                        document.all.DBTableDetail.rows(j).cells(i).style.display = "inline";
                        if (j > 1) {
                            document.all["column" + j + "-" + i].style.width = colWidth;
                        }
                    }
                }
            }
        }
        break;
    }
}
function clsGridRowValue(RowIndex, url, straction) {
		//新增模式下，清除資料
    var colcount;
    var colType;
    var strTmp;
    colcount = document.all.DBTableDetail.rows(RowIndex).cells.length;
    strTmp = "";
    for (var i = 0; i < colcount; i++) {
        colType = document.all.DBTableDetail.rows(1).cells(i).innerText.substring(1, 2);
			//將其他欄位清空
        switch (colType) {
          case "B":  //不用帶
            break;
          default:
            strTmp += document.all["column" + RowIndex + "-" + i].value + " {;}";
        }
    }
    Detail_cancel(url, strTmp, "DBGRIDDetail", "doCancel", straction, RowIndex);
}
function doCancel(RowIndex, str) {
    var ValueArr;
    var colcount;
    var colType;
    var Arrindex;
    ValueArr = str.split("{;}");
    Arrindex = 0;
    if (ValueArr.length != 1) { //成功
        colcount = document.all.DBTableDetail.rows(RowIndex).cells.length;
        for (var i = 0; i < colcount; i++) {
            colType = document.all.DBTableDetail.rows(1).cells(i).innerText.substring(1, 3);
				//將其他欄位清空
            switch (colType) {
              case "BC":  //不用帶
                break;
              case "BD":  //checked == false
                document.all["column" + RowIndex + "-" + i].checked = false;
                break;
              default:
                document.all["column" + RowIndex + "-" + i].value = ValueArr[Arrindex];
                Arrindex = Arrindex + 1;
            }
            document.all.DBTableDetail.rows(RowIndex).cells(0).style.backgroundColor = "";
            if (colType.charAt(1) == "R") {
                document.all["column" + RowIndex + "-" + i].style.backgroundColor = "#E4E4E4";
            } else {
                document.all["column" + RowIndex + "-" + i].style.backgroundColor = "";
            }
        }
    } else {
        alert("\u7a0b\u5f0f\u767c\u751f\u932f\u8aa4\uff1a" + str);
    }
}
function DBLOVReturn(PutFiled_name, Return_Value, RowIndex) {
    var ValueArr; //放值用的
    var NameArr; //放欄位名稱用
    var colcount;
    var strTmp;
    ValueArr = Return_Value.split("{;}");
    NameArr = PutFiled_name.split("{;}");
    colcount = document.all.DBTableDetail.rows(RowIndex).cells.length;
    for (var i = 0; i < colcount; i++) {
        strTmp = document.all.DBTableDetail.rows(0).cells(i).innerText;
        for (var j = 0; j < NameArr.length; j++) {
            if (strTmp == NameArr[j] || strTmp == "\u25ce" + NameArr[j]) {
                document.all["column" + RowIndex + "-" + i].value = ValueArr[j];
                j = NameArr.lengh;
            }
        }
    }
    chgGridRow(RowIndex);
    document.all.BA_DATAGRID_LOV_DIV.style.display = "none";
    document.getElementById("detailDivShim").style.display = "none";
    document.all.DBGRIDDetail.style.visibility = "visible";
}
function chgGridRow(RowIndex) {
    var colcount;
    var colType;
    colcount = document.all.DBTableDetail.rows(RowIndex).cells.length;
    for (var i = 0; i < colcount; i++) {
        colType = document.all.DBTableDetail.rows(1).cells(i).innerText.substring(1, 3);
			//改變底色
        switch (colType) {
          case "BC":  //不用改
            break;
          default:
            document.all["column" + RowIndex + "-" + i].style.backgroundColor = DBGrid_Chang_Color;
        }
        document.all.DBTableDetail.rows(RowIndex).cells(0).style.backgroundColor = DBGrid_Chang_Color;
    }
}
function Dbgrid_Detail_save(url) {
    doSend(url, "ok");
}
function doSend(url, str) {
    var NowRow;
    var colcount;
    var rowIndex;
    var strTmp;
    var colType;
    var bln; //判斷是修改還是刪除
    if (str != "ok") {
			//存檔失敗
			//處理完畢將列數還原
        GridEditNowRow.value = 2;
        alert("Error:" + str);
        DivSetVisible(false, "DBGRIDDetail");
    } else {
			//存檔成功，繼續處理下一筆
			//取得欄位數
        colcount = document.all.DBTableDetail.rows(1).cells.length;
        NowRow = parseInt(GridEditNowRow.value); //取得這次要處理的列數
        if (NowRow > 2) { //拿掉上一筆的顏色
            for (var j = 0; j < colcount; j++) {
                colType = document.all.DBTableDetail.rows(1).cells(j).innerText.substring(1, 3);
                if (colType.charAt(1) == "R") {
                    document.all["column" + (NowRow - 1) + "-" + j].style.backgroundColor = "#E4E4E4";
                } else {
                    document.all["column" + (NowRow - 1) + "-" + j].style.backgroundColor = "";
                }
            }
            document.all.DBTableDetail.rows((NowRow - 1)).cells(0).style.backgroundColor = "";
        }
			//判斷是否執行完畢	
        if (NowRow > document.all.DBTableDetail.rows.length - 1) {
				//處理完畢將列數還原
            GridEditNowRow.value = 2;
            if (GridEditMode.value == "insert") {
                GataGridAdd.onclick();
            } else {
                GataGridCancel.onclick();
            }
        } else {
            GridEditNowRow.value = (NowRow + 1);
            strTmp = "";
            if (document.all.DBTableDetail.rows(NowRow).cells(0).style.backgroundColor == DBGrid_Chang_Color) {
					//表示有異動過
                bln = false;
                for (var j = 0; j < colcount; j++) {
                    colType = document.all.DBTableDetail.rows(1).cells(j).innerText.substring(1, 3);
   						//刪除鈕
                    if (colType == "BD") {
                        if (document.all["column" + NowRow + "-" + j].checked == true) {
                            bln = true;
                        }
                    } else {
                        if (colType == "BC") {
                        } else {
                            strTmp += document.all["column" + NowRow + "-" + j].value + " {;}";
                        }
                    }
                }
                if (bln == true) {
                    Detail_save(url, strTmp, "delete", "DBGRIDDetail", "doSend");
                } else {
                    Detail_save(url, strTmp, GridEditMode.value, "DBGRIDDetail", "doSend");
                }
            } else {
					//沒有異動過
                doSend(url, "ok");
            }
        }
    }
}
function showLovOption(obj, rowIndex) {
    if (obj.value == "6") {
        if (document.all["LOVCol" + rowIndex + "-2"].style.display != "inline") {
            document.all["LOVCol" + rowIndex + "-2"].value = "";
        }
        document.all["LOVCol" + rowIndex + "-2"].style.display = "inline";
    } else {
        document.all["LOVCol" + rowIndex + "-2"].style.display = "none";
        document.all["LOVCol" + rowIndex + "-2"].value = "";
    }
}
function Dbgrid_LOV_ADD_Query() {
		//新增條件式
    var strTmp;
    for (var i = 1; i < 3; i++) {
        strTmp = document.all["LOVCol" + i + "-0"].style.display;
        if (strTmp == "none") {
            strTmp = document.all["LOVCol" + i + "-0"].value.substring(0, 1);
            document.all["LOVCol" + i + "-0"].style.display = "inline";
            if (strTmp == "S") {
                document.all["LOVSelS" + i].style.display = "inline";
                document.all["LOVSelN" + i].style.display = "none";
            } else {
                document.all["LOVSelS" + i].style.display = "none";
                document.all["LOVSelN" + i].style.display = "inline";
            }
            document.all["LOVCol" + i + "-1"].style.display = "inline";
            document.all["LOVCol" + i + "-2"].style.display = "none";
            i = 3;
        }
    }
}
function Dbgrid_LOV_RM_Query() {
		//移除條件
    var strTmp;
    for (var i = 2; i > 0; i = i - 1) {
        strTmp = document.all["LOVCol" + i + "-0"].style.display;
        if (strTmp == "inline") {
            document.all["LOVCol" + i + "-0"].style.display = "none";
            document.all["LOVSelS" + i].style.display = "none";
            document.all["LOVSelN" + i].style.display = "none";
            document.all["LOVSelS" + i].value = "0";
            document.all["LOVSelN" + i].value = "0";
            document.all["LOVCol" + i + "-1"].style.display = "none";
            document.all["LOVCol" + i + "-1"].value = "";
            document.all["LOVCol" + i + "-2"].style.display = "none";
            document.all["LOVCol" + i + "-2"].value = "";
            i = 0;
        }
    }
}
function Lov_Page_Qry(sql, sqlother, cfield_name, lovfield_id, lovfield_name, divform, pagenum, rowindex) {
    var sqlkeyCmd;
    var sqlkeyid;
    var strValue;
    var strValue2;
    var strTmp;
    sqlkeyCmd = "";
    sqlkeyid = "";
    strValue = "";
    strValue2 = "";
    for (var i = 0; i < 3; i++) {
        strTmp = document.all["LOVCol" + i + "-0"].style.display;
        if (strTmp == "inline") {
            sqlkeyid = sqlkeyid + document.all["LOVCol" + i + "-0"].value.substring(1, document.all["LOVCol" + i + "-0"].value.length) + "{;}";
            strTmp = document.all["LOVCol" + i + "-0"].value.substring(0, 1);
            if (strTmp == "S") {
                sqlkeyCmd = sqlkeyCmd + document.all["LOVSelS" + i].value + "{;}";
            } else {
                sqlkeyCmd = sqlkeyCmd + document.all["LOVSelN" + i].value + "{;}";
            }
            strValue = strValue + document.all["LOVCol" + i + "-1"].value + " {;}";
            strValue2 = strValue2 + document.all["LOVCol" + i + "-2"].value + " {;}";
        }
    }
    Lov_select(sql, sqlother, cfield_name, sqlkeyCmd, sqlkeyid, strValue, strValue2, lovfield_id, lovfield_name, divform, pagenum, rowindex);
}
function checkExit(NowPageNum, ChangPageNUM) {
    var rowcount;
    var bln;
		//取得列數
    bln = false;
    rowcount = document.all.DBTableDetail.rows.length;
    for (var i = 2; i < rowcount; i++) {
        if (document.all.DBTableDetail.rows(i).cells(0).style.backgroundColor == DBGrid_Chang_Color) {
            bln = true;
            i = rowcount;
        }
    }
    if (bln == true) {
        if (confirm("\u5c1a\u6709\u8cc7\u6599\u672a\u5b58\u6a94\uff0c\u4f60\u78ba\u5b9a\u8981\u653e\u68c4\u55ce??") == true) {
            GridPerPage.value = (parseInt(NowPageNum) + parseInt(ChangPageNUM));
            return true;
        } else {
            return false;
        }
    } else {
        GridPerPage.value = (parseInt(NowPageNum) + parseInt(ChangPageNUM));
        return true;
    }
}
function ToBeSelect(NowPageNum, ChangPageNUM) {
    if (checkExit(NowPageNum, ChangPageNUM) == true) {
        ischecked = true;
        GataGridCancel.onclick();
    }
}

