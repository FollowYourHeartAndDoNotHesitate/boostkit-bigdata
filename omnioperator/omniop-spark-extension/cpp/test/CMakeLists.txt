add_subdirectory(shuffle)
add_subdirectory(utils)
add_subdirectory(tablescan)

# configure
set(TP_TEST_TARGET tptest)
set(MY_LINK
        utilstest
        shuffletest
        tablescantest
        )

# find gtest package
find_package(GTest REQUIRED)

set (UT_FILES
        tptest.cpp
        shuffle/shuffle_test.cpp
        tablescan/scan_test.cpp
        )

# compile a executable file
add_executable(${TP_TEST_TARGET} ${UT_FILES})
add_dependencies(${TP_TEST_TARGET} ${MY_LINK})

# dependent libraries
target_link_libraries(${TP_TEST_TARGET}
        ${GTEST_BOTH_LIBRARIES}
        ${MY_LINK}
        gtest
        pthread
        stdc++
        dl
        boostkit-omniop-runtime-1.0.0-aarch64
        boostkit-omniop-vector-1.0.0-aarch64
        securec
        spark_columnar_plugin)

target_compile_options(${TP_TEST_TARGET} PUBLIC -g -O2 -fPIC)

# dependent include
target_include_directories(${TP_TEST_TARGET} PRIVATE ${GTEST_INCLUDE_DIRS})

# discover tests
gtest_discover_tests(${TP_TEST_TARGET})
