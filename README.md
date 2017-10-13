# ShadowFactory

Android library that lets add colorful "shadow like" background to Views

### Usage

``` java
	Shadow.Builder.init(this)
		.shadowAll(shadowSize)
		.blur(blurSize)
		.backgroundColorRes(android.R.color.black)
		.shadowColorRes(R.color.grey)
		.build().set(view);
```