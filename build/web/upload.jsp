<%@include file="header.jsp" %>
<div class="row">

    <div class="col-xs-12 col-sm-6">
        Upload a big text file to extract words
        <form action="./upload" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="word"/>
            <input type="file" name="file" />
            <input type="submit" />
        </form>
    </div>

    <div class="col-xs-12 col-sm-6">
        Upload a bigram file to extract words
        <form action="./upload" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="bigram"/>
            <input type="file" name="file" />
            <input type="submit" />
        </form>
    </div>
</div>

<div class="alert alert-info" role="alert">
    ${message}
</div>


<%@include file="footer.jsp" %>