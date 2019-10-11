<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
  <title>dunwu-examples-showcase-server</title>

  <!-- required meta tags -->
  <meta charset="utf-8"/>
  <meta name="description" content="dunwu-examples-showcase-server"/>
  <meta name="author" content="Zhang Peng"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
  <meta name="apple-mobile-web-app-capable" content="yes"/>

  <!-- required css -->
  <link rel="stylesheet" href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css"/>
</head>
<style>
  .navbar-brand {
    font-size: 28px;
  }

  .nav-link {
    font-size: 18px;
    color: #399AB2;
  }

  .nav-link.active {
    color: #FE4165;
  }

  .foot-content {
    font-size: 22px;
    color: slategrey;
  }
</style>

<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
  <a class="navbar-brand" href="/">DUNWU</a>
  <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#headerNavbar"
          aria-controls="headerNavbar"
          aria-expanded="false"
          aria-label="Toggle navigation"
  >
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="headerNavbar">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="https://github.com/dunwu/dunwu">Github</a>
      </li>
    </ul>
  </div>
</nav>

<!-- 页面首部 -->
<main role="main">
  <section class="jumbotron text-center">
    <div class="container">
      <h1 class="display-3">DUNWU</h1>
      <p class="lead text-muted">Dunwu 是一个 Java 脚手架。</p>
      <p>
        <a href="https://github.com/dunwu/" class="btn btn-primary my-2">Github</a>
        <a href="/swagger-ui.html" class="btn btn-secondary my-2" role="button">REST API</a>
      </p>
    </div>
  </section>

  <div class="container text-center"><h2>欢迎访问</h2></div>
</main>

<footer class="footer mt-auto py-3 text-center">
  <div class="container">
    <span class="foot-content">
      <a href="https://github.com/dunwu/dunwu/">dunwu</a> by
      <a href="https://github.com/dunwu/">@Zhang Peng</a>.
    </span>
  </div>
</footer>

<!-- required javascript -->
<!-- 引用的脚本放在末尾以提速 -->
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.4.1/jquery.slim.min.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>



