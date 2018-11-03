解决 IE 9 10 11 等浏览器不支持 <picture> 标签的问题


<script src="picturefill.js"></script>

推荐使用

<head>
  <script>
    // Picture element HTML5 shiv
    document.createElement( "picture" );
  </script>
  <script src="picturefill.js" async></script>
</head>


<picture>
  <!--[if IE 9]><video style="display: none;"><![endif]-->
  <source srcset="examples/images/extralarge.jpg" media="(min-width: 1000px)">
  <source srcset="examples/images/large.jpg" media="(min-width: 800px)">
  <!--[if IE 9]></video><![endif]-->
  <img srcset="examples/images/medium.jpg" alt="…">
</picture>

<picture>
  <!--[if IE 9]><video style="display: none;"><![endif]-->
  <source srcset="examples/images/extralarge.jpg" media="(min-width: 1000px)">
  <source srcset="examples/images/large.jpg" media="(min-width: 800px)">
  <!--[if IE 9]></video><![endif]-->
  <img alt="…">
</picture>

rel: http://scottjehl.github.io/picturefill/