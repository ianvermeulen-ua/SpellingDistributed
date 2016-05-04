<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        
    </head>
    <form action="./correct" method="post">
        <label for="faulty-sentence"> Type your sentence </label>
        <input type="text" name="faulty-sentence"/>
        <input type="submit" />
    </form>
    <div>
        ${correctedString}
    </div>
</html>