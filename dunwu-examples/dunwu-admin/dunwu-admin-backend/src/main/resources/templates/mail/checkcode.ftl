<html>
<head>
  <base target="_blank" />
  <style type="text/css">
    ::-webkit-scrollbar {
      display: none;
    }
  </style>
  <style id="cloudAttachStyle" type="text/css">
    #divNeteaseBigAttach,
    #divNeteaseBigAttach_bak {
      display: none;
    }
  </style>
</head>
<body tabindex="0" role="listitem">
<div id="content">
  <style></style>

  <div
      style="background-color:#d0d0d0;background-image:url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_bg.png);padding:40px;"
  >
    <div
        class="mmsgLetter"
        style="margin:0 auto;padding:10px;color:#333;background-color:#fff;border:0px solid #aaa;border-radius:5px;-webkit-box-shadow:3px 3px 10px #999;-moz-box-shadow:3px 3px 10px #999;box-shadow:3px 3px 10px #999;font-family:Verdana, sans-serif; "
    >
      <div
          class="mmsgLetterHeader"
          style="height:23px;background:url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_bg_topline.png) repeat-x 0 0;"
      ></div>

      <div class="letterLog"><p>Dunwu Quickstart</p></div>

      <div style="text-align:left; margin-top: 20px; padding: 30px 30px 0 30px;">
        <div>
          <p>${to}，您好!</p>
          <p>
            感谢您注册 <strong>Dunwu QuickStart</strong>!<br />
            您的登录邮箱为：<a data-auto-link="1" href="mailto:${to}">${to}</a>。请回填如下6位验证码：
          </p>
          <p style="text-align: center;"><code class="mmsgLetterDigital">${checkCode}</code></p>
          <p>验证码在30分钟内有效，30分钟后需要重新激活邮箱</p>
        </div>

        <div class="mmsgLetterInscribe" style="padding:40px 0 0;">
          <img
              class="mmsgAvatar"
              src="http://dunwu.test.upcdn.net/cs/others/zp.png"
              style="float:left; width:60px; height:60px; margin-right: 10px"
          />
          <div class="mmsgSender" style="margin:0 0 0 54px;">
            <p class="mmsgName" style="margin:0 0 10px;">Dunwu</p>
            <p class="mmsgInfo" style="font-size:12px;margin:0;line-height:1.2;">
              <a href="https://github.com/dunwu/" style="color:#777777;">Github：https://github.com/dunwu/</a>
              <br />
              <a href="mailto:forbreak@163.com" style="color:#777777;">邮箱：forbreak@163.com</a>
            </p>
          </div>
        </div>
      </div>

      <div
          class="mmsgLetterFooter"
          style="margin:16px;text-align:center;font-size:12px;color:#888;text-shadow:1px 0px 0px #eee;"
      ></div>
    </div>
  </div>
</div>

<script>
  var _c = document.getElementById('content');
  _c.innerHTML = (_c.innerHTML || '').replace(/(href|formAction|onclick|javascript)/gi, '__$1')
</script>
<style type="text/css">
  body {
    font-size: 14px;
    font-family: arial, verdana, sans-serif;
    line-height: 1.666;
    padding: 0;
    margin: 0;
    overflow: auto;
    white-space: normal;
    word-wrap: break-word;
    min-height: 100px;
  }

  td,
  input,
  button,
  select,
  body {
    font-family: Helvetica, "Microsoft Yahei", verdana;
  }

  pre {
    white-space: pre-wrap;
    white-space: -moz-pre-wrap;
    white-space: -pre-wrap;
    white-space: -o-pre-wrap;
    word-wrap: break-word;
    width: 95%;
  }

  th,
  td {
    font-family: arial, verdana, sans-serif;
    line-height: 1.666;
  }

  img {
    border: 0;
  }

  header,
  footer,
  section,
  aside,
  article,
  nav,
  hgroup,
  figure,
  figcaption {
    display: block;
  }

  .letterLog {
    font-size: 24px;
    font-family: Impact, Elephant, serif, Arial;
    font-weight: bold;
    text-align: center;
    background-image: -webkit-linear-gradient(left, #ffdcb4, #b96972 25%, #e88a57 50%, #804170 75%, #a596cd);
    -webkit-text-fill-color: transparent;
    -webkit-background-clip: text;
    -webkit-background-size: 200% 100%;
    -webkit-animation: masked-animation 1.5s infinite linear;
    float: right;
    padding-right: 30px;
  }

  blockquote {
    margin-right: 0px;
  }

  @-webkit-keyframes masked-animation {
    0% {
      background-position: 0 0;
    }
    100% {
      background-position: -100% 0;
    }
  }

  .mmsgLetterDigital {
    color: #24b0cf;
    background: #fafafa;
    border: 1px solid #24b0cf;
    box-shadow: 1px 1px 1px whitesmoke;
    font-size: 16px;
    margin: 0 2px;
    padding: 5px 5px;
    white-space: pre-wrap;
  }
</style>

<style id="netease_mail_footer_style" type="text/css">
  #netease_mail_footer {
    display: none;
  }
</style>
</body>
</html>
