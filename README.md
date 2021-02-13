
<p>持续配置中</p>

<br>

在项目根目录的 build.gradle 添加仓库

```groovy
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

在 module 的 build.gradle 添加依赖

```groovy
    implementation 'com.github.SpielbergGao:CommonEx:v1.0.1'
```

<br>



## Contribute

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1gaskr305czj30u00wjtcz.jpg" width="100"/> 

supported by [JetBrains](https://www.jetbrains.com/)


## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```