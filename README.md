java工程中加入Kotlin文件，会导致原有的java中使用的标签语法无效，如：

    1.Dagger2，必须将Dagger2的注解引擎annotationProcessor改为kapt；
    2.ButterKnife8,必须将其注解引擎annotationProcessor改为kapt，并将java文件中的变量加入@JvmField

Glide4,这个版本目前与Kotlin不兼容，需要引入GlideApp的概念，并在AndroidManifest中静态声明

十分不建议Java与Kotlin混用

    必须混用的情况下，建议Kotlin作为界面罗技，Java为工具类语言
    
现阶段，Kotlin还是十分不成熟，只限定与“尝鲜”