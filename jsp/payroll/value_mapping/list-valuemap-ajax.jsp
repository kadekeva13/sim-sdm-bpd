<%-- 
    Document   : list-valuemap-ajax
    Created on : Mar 11, 2016, 8:58:56 AM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployeeView"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.payroll.PstValue_Mapping"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlValue_Mapping"%>
<%@page import="com.dimata.harisma.entity.payroll.Value_Mapping"%>
<%@ include file = "../../main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String gradeCode= FRMQueryString.requestString(request, "grade_code");
    String compCode = FRMQueryString.requestString(request, "comp_code");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String whereClause = "";
    String order = "";
    Vector listValueMap = new Vector();
    CtrlValue_Mapping ctrlMapping = new CtrlValue_Mapping(request);
    SessEmployeeView sessEmpView = new SessEmployeeView();
    int recordToGet = 10;
    int vectSize = 0;
    vectSize = PstValue_Mapping.getCount("");
    if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
            || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
        start = ctrlMapping.actionList(iCommand, start, vectSize, recordToGet);
    }

    if (!(gradeCode.equals("0"))){
        whereClause  = PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_GRADE]+" LIKE '%"+gradeCode+"%'";
        whereClause += " AND " + PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_COMP_CODE]+"='"+compCode+"'";
        vectSize = PstValue_Mapping.getCount(whereClause);
        listValueMap = PstValue_Mapping.list(0, 0, whereClause, order);        
    } else {
        whereClause = PstValue_Mapping.fieldNames[PstValue_Mapping.FLD_COMP_CODE]+"='"+compCode+"'";
        vectSize = PstValue_Mapping.getCount(whereClause);
        if (iCommand == Command.LAST){
            start = vectSize - 10;
        }
        listValueMap = PstValue_Mapping.list(start, recordToGet, whereClause, order);
    }

    if (listValueMap != null && listValueMap.size()>0){
        %>
        <table class="tblStyle" style="background-color: #F5F5F5">
            <tr>
                <td class="title_tbl">No</td>
                <td class="title_tbl">Start Date</td>
                <td class="title_tbl">End Date</td>
                <td class="title_tbl">Company</td>
                <td class="title_tbl">Division</td>
                <td class="title_tbl">Department</td>
                <td class="title_tbl">Section</td>
                <td class="title_tbl">Level</td>
                <td class="title_tbl">Marital</td>
                <td class="title_tbl">Length Of Service</td>
                <td class="title_tbl">Employee Category</td>
                <td class="title_tbl">Position</td>
                <td class="title_tbl">Grade</td>
                <td class="title_tbl">Payroll Num</td>
                <td class="title_tbl">GEO</td>
                <td class="title_tbl">Sex</td>
                <td class="title_tbl">Value</td>
                <td class="title_tbl">Action</td>
            </tr>
        <%
        for(int i=0; i<listValueMap.size(); i++){
            Value_Mapping valueMapping = (Value_Mapping)listValueMap.get(i);
            String geo = PstValue_Mapping.GetGeoAddress(valueMapping); 
            String sex = "-";
            if (valueMapping.getSex() > -1){
                if (valueMapping.getSex()== 0){
                    sex = "Pria";
                } else {
                    sex = "Wanita";
                }
            }
            %>
            <tr>
                <td><%=(i+1)%></td>
                <td><%=""+valueMapping.getStartdate()%></td>
                <td><%=""+valueMapping.getEnddate()%></td>
                <td><%=""+sessEmpView.getCompanyName(valueMapping.getCompany_id())%></td>
                <td><%=""+sessEmpView.getDivisionName(valueMapping.getDivision_id())%></td>
                <td><%=""+sessEmpView.getDepartmentName(valueMapping.getDepartment_id())%></td>
                <td><%=""+sessEmpView.getSectionName(valueMapping.getSection_id())%></td>
                <td><%=""+sessEmpView.getLevelName(valueMapping.getLevel_id())%></td>
                <td><%=""+sessEmpView.getMaritalName(valueMapping.getMarital_id()) %></td>
                <td><%=""+valueMapping.getLength_of_service() %></td>
                <td><%=""+sessEmpView.getEmpCategory(valueMapping.getEmployee_category())%></td>
                <td><%=""+sessEmpView.getPositionName(valueMapping.getPosition_id())%></td>
                <td><%=""+sessEmpView.getGradeLevel(valueMapping.getGrade())%></td>
                <td><%=""+sessEmpView.getFullNameAndPayroll(valueMapping.getEmployee_id()) %></td>
                <td><%= geo %></td>
                <td><%= sex %></td>
                <td><%=Formater.formatNumber(valueMapping.getValue(), "")%></td>
                <td>
                    <a class="btn-small-e" style="color:#FFF" href="javascript:cmdEdit('<%=valueMapping.getOID()%>','<%=valueMapping.getCompCode()%>')">e</a>
                    <a class="btn-small-x" style="color:#FFF" href="javascript:cmdAsk('<%=valueMapping.getOID()%>')">&times;</a>
                </td>
            </tr>
            <%
        }
        %>
        </table>
        <div>&nbsp;</div>
        <div id="record_count">
            <%
            if (vectSize >= recordToGet){
                %>
                List : <%=start%> &HorizontalLine; <%= (start+recordToGet) %> | 
                <%
            }
            %>
            Total : <%= vectSize %>
        </div>
        <div class="pagging">
            <a style="color:#F5F5F5" href="javascript:cmdListFirst('<%=start%>','<%=compCode%>')" class="btn-small">First</a>
            <a style="color:#F5F5F5" href="javascript:cmdListPrev('<%=start%>','<%=compCode%>')" class="btn-small">Previous</a>
            <a style="color:#F5F5F5" href="javascript:cmdListNext('<%=start%>','<%=compCode%>')" class="btn-small">Next</a>
            <a style="color:#F5F5F5" href="javascript:cmdListLast('<%=start%>','<%=compCode%>')" class="btn-small">Last</a>
        </div>
        <%
    } else {
        %>
        <p id="menu_utama">No Data</p>
        <%
        
    }
%>
