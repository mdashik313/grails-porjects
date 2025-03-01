<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="POS"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
    <g:layoutHead/>
    <r:layoutResources />
    <style>
        #projectTitle {
            text-align: left;
            background-color:rgb(39, 156, 43);
            padding: 10px;
            border-radius: 0;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        #projectTitle h1 {
            color: white;
            font-family: Arial, sans-serif;
            font-size: 2.5em;
            margin: 0;
            text-transform: uppercase;
            letter-spacing: 2px;
            font-weight: bold;
        }

        #projectTitle a {
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div id="projectTitle" role="banner">
        <a href="/">
            <h1>POS</h1> <!-- Your project name here -->
        </a>
    </div>
    
    <g:layoutBody/>
    <div class="footer" role="contentinfo"></div>
    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>
    
    <g:javascript library="application"/>
    <r:layoutResources />
</body>
</html>
