<%-- 
    Document   : organization_search-ajax
    Created on : Jan 25, 2016, 10:50:22 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.PositionDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.MappingPosition"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstMappingPosition"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_ORGANISASI, AppObjInfo.OBJ_MENU_STRUKTUR_ORGANISASI); %>
<%@ include file = "../main/checkuser.jsp" %>
<%!
    public String getDivision(long positionId){
        String str = "";
        String whereClause = PstPositionDivision.fieldNames[PstPositionDivision.FLD_POSITION_ID]+"="+positionId;
        Vector listPosDivisi = PstPositionDivision.list(0, 0, whereClause, "");
        if (listPosDivisi != null && listPosDivisi.size()>0){
            for(int i=0; i<listPosDivisi.size(); i++){
                PositionDivision posDiv = (PositionDivision)listPosDivisi.get(i);
                str += posDiv.getDivisionId() +", ";
            }
            str += "0";
        }
        return str;
    }

    public String getDivisionName(long divisionId){
        String name = "-";
        if (divisionId != 0) {
            try {
                Division division = PstDivision.fetchExc(divisionId);
                name = division.getDivision();
            } catch (Exception e) {
                System.out.println("Division Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getDepartmentName(long departmentId){
        String name = "-";
        if (departmentId != 0){
            try {
                Department depart = PstDepartment.fetchExc(departmentId);
                name = depart.getDepartment();
            } catch (Exception e) {
                System.out.println("Department Name =>" + e.toString());
            }
        }
        return name;
    }

    public String getSectionName(long sectionId){
        String name = "-";
        if (sectionId != 0){
            try {
                Section section = PstSection.fetchExc(sectionId);
                name = section.getSection();
            } catch (Exception e) {
                System.out.println("Section Name =>" + e.toString());
            }
        }
        return name;
    }

    public String getStructureTemplateName(long oid){
        String name = "-";
        if (oid != 0){
            try {
                StructureTemplate tmpName = PstStructureTemplate.fetchExc(oid);
                name = tmpName.getTemplateName();
            } catch(Exception e){
                System.out.println("structure name=>"+e.toString());
            }
        }
        return name;
    }
%>
<%
    long selectStructure = FRMQueryString.requestLong(request, "select_structure");
    String structureName = getStructureTemplateName(selectStructure);
    if (selectStructure != 0){
        String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+selectStructure;
        Vector listMapping = PstMappingPosition.listDistinct(whereClause);
        String strPosition = "";
        if (listMapping != null && listMapping.size()>0){
            for(int i=0; i<listMapping.size(); i++){
                Long mapping = (Long)listMapping.get(i);
                strPosition += mapping + ", ";
            }
            strPosition += "-1";
            String wherePos = PstPositionDivision.fieldNames[PstPositionDivision.FLD_POSITION_ID]+" IN ("+strPosition+")";
            Vector listDivision = PstPositionDivision.listDistinct(wherePos);
            %>
            <table cellpadding="5" cellspacing="5">
                <tr>
                    <td valign="top" colspan="3">
                        <div id="note">
                            Struktur Organisasi <%=structureName%> tersedia pada:
                        </div>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <%
                        if (listDivision != null && listDivision.size()>0){
                            %>
                            <div id="menu_sub">
                                <span id="menu_title"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></span>
                            </div>
                            <%
                            for (int j=0; j<listDivision.size(); j++){
                                Long divisi = (Long)listDivision.get(j);
                                %>
                                <a href="javascript:cmdView('<%=selectStructure%>','<%=divisi%>')">
                                <div id="floatleft">
                                    <%=getDivisionName(divisi)%>
                                </div>
                                </a>
                                <%
                            }
                        }
                        %>
                    </td>
                    <td valign="top">
                        <%
                        Vector listDept = PstPositionDepartment.listDistinct(wherePos);
                        if (listDept != null && listDept.size()>0){
                            %>
                            <div id="menu_sub">
                                <span id="menu_title"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></span>
                            </div>
                            <%
                            for(int k=0; k<listDept.size(); k++){
                                Long dept = (Long)listDept.get(k);
                                Department depart = PstDepartment.fetchExc(dept);
                                %>
                                <a href="javascript:cmdViewByDept('<%=selectStructure%>','<%=dept%>')">
                                <div id="floatleft">
                                    <%= getDivisionName(depart.getDivisionId()) %> / <%=getDepartmentName(dept)%>
                                </div>
                                </a>
                                <%
                            }
                        }
                        %>
                    </td>
                    <td valign="top">
                        <%
                        Vector listSection = PstPositionSection.listDistinct(wherePos);
                        if (listSection != null && listSection.size()>0){
                            %>
                            <div id="menu_sub">
                                <span id="menu_title"><%=dictionaryD.getWord("SECTION")%></span>
                            </div>
                            <%
                            for(int l=0; l<listSection.size(); l++){
                                Long section = (Long)listSection.get(l);
                                %>
                                <a href="javascript:cmdViewBySect('<%=selectStructure%>','<%=section%>')">
                                <div id="floatleft">
                                    <%=getSectionName(section)%>
                                </div>
                                </a>
                                <%
                            }
                        }
                        %>
                    </td>
                </tr>
            </table>
            <%
        }
    }
%>