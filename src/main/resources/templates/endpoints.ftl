<html>
    <head>
        <title>Actuator endpoints</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"/>
    </head>
    <body>
            <div class="container">
                <div class="page-header">
                    <h1>Spring boot actuator endpoints</h1>
                </div>
            <div class="panel panel-default">
                <ul class="list-group">
                <#list endpoints as endpoint>
                    <li class="list-group-item"><a href="${endpoint}">${endpoint}</a></li>
                </#list>
                </ul>
            </div>
        </div>
    </body>
</html>