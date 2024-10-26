package com.yudiz.testing.sample

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(EmailTest::class, PasswordTest::class)
class SuiteTest