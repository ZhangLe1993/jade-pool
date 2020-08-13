<template>
    <div class="uc58VLkUPAa4QHX5E1YfL">
        <div class="_2p9cXjtfcKyHCQ6a_lJiQW">
            <div class="_3fCS1j5LMaW9HtYVl6ipPm"><i aria-label="图标: user" class="anticon anticon-user">
                <svg viewBox="64 64 896 896" focusable="false" class="" data-icon="user" width="1em" height="1em"
                     fill="currentColor" aria-hidden="true">
                    <path d="M858.5 763.6a374 374 0 0 0-80.6-119.5 375.63 375.63 0 0 0-119.5-80.6c-.4-.2-.8-.3-1.2-.5C719.5 518 760 444.7 760 362c0-137-111-248-248-248S264 225 264 362c0 82.7 40.5 156 102.8 201.1-.4.2-.8.3-1.2.5-44.8 18.9-85 46-119.5 80.6a375.63 375.63 0 0 0-80.6 119.5A371.7 371.7 0 0 0 136 901.8a8 8 0 0 0 8 8.2h60c4.4 0 7.9-3.5 8-7.8 2-77.2 33-149.5 87.8-204.3 56.7-56.7 132-87.9 212.2-87.9s155.5 31.2 212.2 87.9C779 752.7 810 825 812 902.2c.1 4.4 3.6 7.8 8 7.8h60a8 8 0 0 0 8-8.2c-1-47.8-10.9-94.3-29.5-138.2zM512 534c-45.9 0-89.1-17.9-121.6-50.4S340 407.9 340 362c0-45.9 17.9-89.1 50.4-121.6S466.1 190 512 190s89.1 17.9 121.6 50.4S684 316.1 684 362c0 45.9-17.9 89.1-50.4 121.6S557.9 534 512 534z"></path>
                </svg>
            </i>
                <input placeholder="用户名" value="11">
            </div>
            <div class="_3fCS1j5LMaW9HtYVl6ipPm"><i aria-label="图标: unlock" class="anticon anticon-unlock">
                <svg viewBox="64 64 896 896" focusable="false" class="" data-icon="unlock" width="1em" height="1em"
                     fill="currentColor" aria-hidden="true">
                    <path d="M832 464H332V240c0-30.9 25.1-56 56-56h248c30.9 0 56 25.1 56 56v68c0 4.4 3.6 8 8 8h56c4.4 0 8-3.6 8-8v-68c0-70.7-57.3-128-128-128H388c-70.7 0-128 57.3-128 128v224h-68c-17.7 0-32 14.3-32 32v384c0 17.7 14.3 32 32 32h640c17.7 0 32-14.3 32-32V496c0-17.7-14.3-32-32-32zm-40 376H232V536h560v304zM484 701v53c0 4.4 3.6 8 8 8h40c4.4 0 8-3.6 8-8v-53a48.01 48.01 0 1 0-56 0z"></path>
                </svg>
            </i><input placeholder="密码" type="password" value="1"></div>
        </div>
        <button style="cursor: pointer">登 录</button>
        <p class="_1PwP8YzfxL769AEzyWD3yf"><span>还没有账号？ </span><a href="javascript:;">注册账号</a></p></div>
</template>

<script>
    export default {
        name: "LoginForm",
        data() {
            var checkAge = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('年龄不能为空'));
                }
                setTimeout(() => {
                    if (!Number.isInteger(value)) {
                        callback(new Error('请输入数字值'));
                    } else {
                        if (value < 18) {
                            callback(new Error('必须年满18岁'));
                        } else {
                            callback();
                        }
                    }
                }, 1000);
            };
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                ruleForm: {
                    pass: '',
                    checkPass: '',
                    age: ''
                },
                rules: {
                    pass: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validatePass2, trigger: 'blur'}
                    ],
                    age: [
                        {validator: checkAge, trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>

    .container {
        width: 320px;
        background-color: rgba(34, 49, 81, 0.5);
        padding-top: 64px;
        margin-bottom: 180px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        position: relative;
        z-index: 1;
    }
    .uc58VLkUPAa4QHX5E1YfL {
        margin: 0 auto;
        margin-top: 200px;
        width: 320px;
        background-color: rgba(34, 49, 81, 0.5);
        padding-top: 64px;
        margin-bottom: 180px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        position: relative;
        z-index: 1;
    }
    .uc58VLkUPAa4QHX5E1YfL ._2p9cXjtfcKyHCQ6a_lJiQW {
        width: 100%;
        margin-bottom: 32px;
    }
    *, *::before, *::after {
        box-sizing: border-box;
    }
    div {
        display: block;
    }
    .uc58VLkUPAa4QHX5E1YfL {
        width: 320px;
        background-color: rgba(34, 49, 81, 0.5);
        padding-top: 64px;
        margin-bottom: 180px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        position: relative;
        z-index: 1;
    }
    ._3KAOa4_oomjlrQkVsvTXmF {
        background: #223151;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
        -webkit-box-pack: center;
        -ms-flex-pack: center;
        justify-content: center;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
    }
    body {
        margin: 0;
        color: rgba(0, 0, 0, 0.65);
        font-size: 14px;
        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';
        font-variant: tabular-nums;
        line-height: 1.5;
        background-color: #fff;
        font-feature-settings: 'tnum';
    }
    ._3fCS1j5LMaW9HtYVl6ipPm {
        height: 45px;
        border-bottom: 1px solid #fff;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: horizontal;
        -webkit-box-direction: normal;
        -ms-flex-direction: row;
        flex-direction: row;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
    }
    .anticon {
        display: inline-block;
        color: inherit;
        font-style: normal;
        line-height: 0;
        text-align: center;
        text-transform: none;
        vertical-align: -0.125em;
        text-rendering: optimizeLegibility;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }
    ._3fCS1j5LMaW9HtYVl6ipPm:last-child {
        border-bottom: 0;
    }
    ._3fCS1j5LMaW9HtYVl6ipPm {
        height: 45px;
        border-bottom: 1px solid #fff;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: horizontal;
        -webkit-box-direction: normal;
        -ms-flex-direction: row;
        flex-direction: row;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
    }
    ._3fCS1j5LMaW9HtYVl6ipPm input {
        -webkit-box-flex: 1;
        -ms-flex: 1;
        flex: 1;
        background-color: transparent;
        padding: 0 8px;
        border: 0;
        outline: none;
        color: #fff;
        font-size: 1.15em;
    }

    ._3fCS1j5LMaW9HtYVl6ipPm input {
        -webkit-box-flex: 1;
        -ms-flex: 1;
        flex: 1;
        background-color: transparent;
        padding: 0 8px;
        border: 0;
        outline: none;
        color: #fff;
        font-size: 1.15em;
    }
    ._3fCS1j5LMaW9HtYVl6ipPm i {
        color: #fff;
        font-size: 24px;
        -ms-flex-negative: 0;
        flex-shrink: 0;
    }
    .uc58VLkUPAa4QHX5E1YfL button {
        width: 75%;
        height: 36px;
        background-color: transparent;
        border: 1px solid #fff;
        border-radius: 3px;
        color: #fff;
        font-size: 1.15em;
        outline: none;
    }
    button, html [type="button"], [type="reset"], [type="submit"] {
        -webkit-appearance: button;
    }
    button, select {
        text-transform: none;
    }
    button, input {
        overflow: visible;
    }
    input, button, select, optgroup, textarea {
        margin: 0;
        color: inherit;
        font-size: inherit;
        font-family: inherit;
        line-height: inherit;
    }
    ._1PwP8YzfxL769AEzyWD3yf {
        margin-top: 16px;
    }

    ._1PwP8YzfxL769AEzyWD3yf span {
        color: #f7f7f7;
    }
    ._1PwP8YzfxL769AEzyWD3yf a {
        font-weight: 600;
    }
    a {
        color: #1B98E0;
        text-decoration: none;
        background-color: transparent;
        outline: none;
        cursor: pointer;
        transition: color 0.3s;
        -webkit-text-decoration-skip: objects;
    }
    a, area, button, [role='button'], input:not([type='range']), label, select, summary, textarea {
        touch-action: manipulation;
    }
    input::-webkit-input-placeholder, textarea::-webkit-input-placeholder {
        /* WebKit browsers */
        color: #fff;
    }
    input:-moz-placeholder, textarea:-moz-placeholder {
        /* Mozilla Firefox 4 to 18 */
        color: #fff;
    }
    input::-moz-placeholder, textarea::-moz-placeholder {
        /* Mozilla Firefox 19+ */
        color: #fff;
    }
    input:-ms-input-placeholder, textarea:-ms-input-placeholder {
        /* Internet Explorer 10+ */
        color: #fff;
    }
</style>