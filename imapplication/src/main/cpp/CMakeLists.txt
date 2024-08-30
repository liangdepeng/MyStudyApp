# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# 设置构建原生库所需的最低 CMake 版本。

cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

project("imlib")

# 创建并命名一个库，将其设置为 STATIC
# 或 SHARED，并提供其源代码的相对路径。
# 您可以定义多个库，CMake 会为您构建它们。
# Gradle 会自动将共享库与您的 APK 打包在一起。

add_library( # Sets the name of the library.
             imlib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             imlib.cpp )

# 搜索指定的预构建库，并将路径存储为
# 变量。由于 CMake 在搜索路径中包含系统库
# 默认，只需指定公共 NDK 库的名称即可
# 你要添加。 CMake 验证该库是否存在
# 完成构建。

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# 指定 CMake 应链接到目标库的库。你
# 可以链接多个库，比如你在此定义的库
# 构建脚本、预构建的第三方库或系统库。

target_link_libraries( # Specifies the target library.
                       imlib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )