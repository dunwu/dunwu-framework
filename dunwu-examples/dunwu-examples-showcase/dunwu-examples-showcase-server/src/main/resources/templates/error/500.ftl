<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
	<title>dunwu</title>

	<!-- required meta tags -->
	<meta charset="utf-8" />
	<meta name="description" content="dunwu-examples-showcase-server" />
	<meta name="author" content="Zhang Peng" />
	<meta
			name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no"
	/>
	<meta name="apple-mobile-web-app-capable" content="yes" />

	<!-- required css -->
	<link
			rel="stylesheet"
			href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css"
	/>
</head>
<style>
	.navbar-brand {
		font-size: 28px;
	}

	.nav-link {
		font-size: 18px;
		color: #399ab2;
	}

	.nav-link.active {
		color: #fe4165;
	}

	.foot-content {
		font-size: 22px;
		color: slategrey;
	}

	.error h1 {
		margin-bottom: 24px;
		color: #434e59;
		font-weight: 600;
		font-size: 72px;
		line-height: 72px;
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
	<div class="bg-light">
		<div class="container">
			<div class="row" style="min-height: 500px;">
				<div class="col-md-1"></div>
				<div class="col-md-4 m-auto">
					<img src="http://dunwu.test.upcdn.net/common/web/500.svg" />
				</div>
				<div class="col-md-4 m-auto text-center error">
					<h1>500</h1>
					<h2>抱歉，服务器出错了</h2>
					<a href="/" class="btn btn-primary my-2">返回首页</a>
				</div>
				<div class="col-md-1"></div>
			</div>
		</div>
	</div>
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
<script
		type="text/javascript"
		src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"
></script>
<script
		type="text/javascript"
		src="https://cdn.bootcss.com/jquery/3.4.1/jquery.slim.min.js"
></script>
<script
		type="text/javascript"
		src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js"
></script>
</body>
</html>

