<%@include file="header.jsp" %>
    <form action="./correct" method="post">
        <label for="faulty-sentence"> Type your sentence </label>
        <input type="text" name="faulty-sentence"/>
        <input type="submit" />
    </form>
    <div>
        ${correctedString}
    </div>
<%@include file="footer.jsp" %>