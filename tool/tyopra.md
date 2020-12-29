# Typora 伟大的软件

## Markdown 基本语法

- 标题

- 字体

  ```markdown
  **加粗**
  *斜体*
  ***斜体加粗***
  ~~删除线~~
  
  ```

- 引用

  ```markdown
  >这是引用的内容
  >>这是引用的内容
  >>>>>>>>>>这是引用的内容
  ```

- 分割线

  ```markdown
  <!-- 三个或者三个以上的 - 或者 * 都可以。显示效果是一样的。--> 
  ---
  ----
  ***
  *****
  ```

- 图片

  ```markdown
  ![图片alt](图片地址 ''图片title'')
  
  图片alt就是显示在图片下面的文字，相当于对图片内容的解释。
  图片title是图片的标题，当鼠标移到图片上时显示的内容。title可加可不加
  ```

- 超链接

  ```markdown
  [超链接名](超链接地址 "超链接title")
  title可加可不加
  ```

- 列表

- 表格

- 代码

- 流程图

  ```flow
  st=>start: 开始
  op=>operation: My Operation
  cond=>condition: Yes or No?
  e=>end
  st->op->cond
  cond(yes)->e
  cond(no)->op
  ```





## 文档跳转

> 从一个文档跳到另一个文档的功能,并且自带锚点

### 配置

- 在Typora的安装目录 `.\Typora\resources\app\app\window\frame.js` 增加下面的代码

```js
var content = document.getElementsByTagName("content")[0];
var jumpTime = 100;
var jumpCount = 5;
var write = document.getElementById("write");
content.addEventListener("mouseover", function() {
    window.setTimeout(function() {
        var elements = document.querySelectorAll("a");
        // 为每一个标记都添加事件
        for (let index = 0; index < elements.length; index++) {
            const element = elements[index];
            element.addEventListener("mouseover", function(e) {
                var reg = new RegExp('\#[^\"]+', ["g"]);
                var outText = element.outerHTML;
                var jumpValue = decodeURI(reg.exec(outText)[0]);
                window.localStorage.setItem("jump", jumpValue);
                console.log(jumpValue);
            }, true);
        }
    }, 1000);
},true);

// 跳转文档后，使用锚点
content.addEventListener("load", function() {
    window.setTimeout(function() {
        var value = localStorage.getItem("jump");
        if(value != null){
           var name = value.substring(1,value.length);
           var element;
           var hs = document.querySelectorAll("h1,h2,h3,h4,h5,h6");
           var top;
           // 先搜索标题
           hs.forEach(function(h) {
               if (h.innerText == name) {
                   element = h;
                   top = h.offsetTop;
                   return;
               }
           });
           // 再搜索name
           if(element == undefined){
               var selector = "[name=" + name + "]";
               element = document.querySelector(selector);
               top = findTop(element);
           }
           content.scrollTop=top;
           console.log("跳转高度:"  + top);
       }
    }, 1000);
},true);

// 删除锚点
content.addEventListener("load", function() {
    window.setTimeout(function() {
       	 console.log("删除");
        // 开始删除jump
        localStorage.removeItem("jump");
    }, 30000);
},true);

// 解除Typora的登录限制功能，结合百度脑图
content.addEventListener("mouseover", function() {
    window.setTimeout(function() {
	    var iframeDivs = document.getElementsByName("iframe");
        iframeDivs.forEach((iframeDiv)=>{
        	if (iframeDiv!=null&&iframeDiv.childElementCount == 0) {
	            var iframe = document.createElement("iframe");
	            iframe.src = iframeDiv.getAttribute("style");
		    iframe.sandbox = "allow-scripts allow-same-origin allow-popups allow-top-navigation allow-pointer-lock allow-forms";
	            iframe.scrolling = "no";
	            iframe.height = "100%";
	            iframe.width = "100%";
	            iframeDiv.appendChild(iframe);
	        }
        });
    }, 1000);
},true);

// 添加业内返回跳转功能
content.addEventListener("mouseover", function() {
    window.setTimeout(function() {
        var elements = document.querySelectorAll("a");
        // 为每一个标记都添加事件
        for (let index = 0; index < elements.length; index++) {
            const element = elements[index];
            element.addEventListener("click", function(e) {
			   if(e.ctrlKey==true){
			    var footer = document.getElementsByTagName("footer")[0];
            	var buttons =  document.querySelectorAll("[name=backButton]");
            	if (buttons.length != 0) {
            		removeChilds(footer,buttons);
            	}
            	
        		var button = document.createElement("button");
        		var cancelButton = document.createElement("button");
	        	// 跳转的按钮
	        	button.innerText = "返回";
		        button.name="backButton";
		        button.className = "back-button";
		       	// 清除的按钮
		        cancelButton.innerText = "取消";
		        cancelButton.name="backButton";
		        cancelButton.className = "cancel-button";

		        footer.append(button);
		        footer.append(cancelButton);

		        buttons =  document.querySelectorAll("[name=backButton]");

		        button.onclick = (e) => {
		            e.cancelBubble = true;
		            var jumpValue = localStorage.getItem("jump");
		          	var top = findTop(element);
		            jumpTo(top,jumpTime,jumpCount);
 					console.log("跳转高度:"  + top);
 					console.log(buttons);
		            removeChilds(footer,buttons);
		        };

		        cancelButton.onclick = (e) => {
		         	e.cancelBubble = true;
		         	console.log(buttons);
		         	removeChilds(footer,buttons);
		        };
		    }
            }, true);
        }
    }, 1000);
},true);

/* 公共函数 */

function removeChilds(parent,children){
	children.forEach((child,index)=>{
		parent.removeChild(child);
	});
}

function findTop(element){
	while(element.parentElement != write){
		element = element.parentElement;
    }
    console.log(element);
	return element.offsetTop;
}

// 匀速跳转
function jumpTo(to, time, count) {
    var from = content.scrollTop;
    var length = to - from;
    var everyLength = parseFloat(length / count);
    var jumpCount=0;

    var interval = window.setInterval(() => {
        if(jumpCount++>=count){
        	content.scrollTop = to;
        	clearInterval(interval);
        }else{
        	content.scrollTop += everyLength;
        }
    }, time / count);
}
```

### 功能的使用

- 起跳节点: 

- ```js
  <!--方式1-->
  <a href="./2.md#test">testJump</a>
  <!--方式2-->
  [testJump](./2.md#test)
  ```

- 锚点节点:

- ```js
  <!--锚点可以是标题-->
  ## test
  <!--锚点可以位于文章中-->
  <span name="test" >hello</span>
  ```

