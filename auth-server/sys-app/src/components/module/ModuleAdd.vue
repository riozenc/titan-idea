<template lang="html">
  <el-dialog :title="$t('constant.module.ADD_MODULE')" :visible.sync="addModuleShow">
    <el-form ref="moduleAddForm" :model="form" label-width="160px" :rules="formRules" :inline="true">
      <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('constant.module.SYSTEM_NAME')">
              {{ systemName }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('constant.module.PARENT_MODULE')">
              {{ menuId ? menuName : ''}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="menuCode" :label="$t('constant.module.MODULE_CODE')">
              <el-input v-model="form.menuCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="menuName" :label="$t('constant.module.MODULE_NAME')">
              <el-input v-model="form.menuName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="menuUrl" :label="$t('constant.module.MODULE_PATH')">
              <el-input v-model="form.menuUrl"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('constant.module.ICON')">
              <el-input v-model="form.imgSrc"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
           <el-col :span="12">
            <el-form-item :label="$t('constant.module.ACTIVE')">
              <el-switch
                v-model="form.status"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-text="$t('constant.TRUE')"
                :inactive-text="$t('constant.FALSE')"
                :active-value="1"
                :inactive-value="0">
              </el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('constant.module.SORT')">
              <el-input-number v-model="form.sortNo" :min="0" :max="200"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('constant.module.IS_MENU')">
              <el-switch
                v-model="form.isLeaf"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-text="$t('constant.TRUE')"
                :inactive-text="$t('constant.FALSE')"
                :active-value="1"
                :inactive-value="0">
              </el-switch>
            </el-form-item>
          </el-col>
          <!--
          <el-col :span="12" v-show="form.isLeaf">
            <el-form-item :label="$t('constant.module.HTTP_METHOD')">
              <el-select v-model="form.httpMethod" :placeholder="$t('constant.module.SELECT')">
                <el-option
                  v-for="item in options"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          -->
        </el-row>
   </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addModuleShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveModule" :loading="addModuleLoading">{{$t('button.SURE')}}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    var validateModuleCode = (rule, value, callback) => {
      self.$http.get(`${DataMainApi}/menu/validate/${self.form.menuCode}`)
        .then(res => {
          if (res.data.code === Status.success) {
            callback()
          } else {
            callback(new Error(self.$t('constant.module.MODULE_CODE_EXIST_NOTIFY')))
          }
        })
    }
    return {
      form: {
        menuCode: null,
        menuName: null,
        menuUrl: null,
        parentId: null,
        imgSrc: null,
        isLeaf: 0,
        sortNo: 0,
        systemId: null,
        status: 1
      },
      // 表单验证
      formRules: {
        menuCode: [
          { required: false, message: self.$t('constant.module.MODULE_CODE_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 32, message: self.$t('constant.module.THREE_TO_32'), trigger: 'blur' },
          { validator: validateModuleCode, trigger: 'blur' }
        ],
        menuName: [
          { required: true, message: self.$t('constant.module.MODULE_NAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 32, message: self.$t('constant.module.THREE_TO_32'), trigger: 'blur' }
        ],
        menuUrl: [
          { required: false, message: self.$t('constant.module.MODULE_PATH_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 100, message: self.$t('constant.module.THREE_TO_100'), trigger: 'blur' }
        ]
      },
      addModuleShow: false,
      addModuleLoading: false,
      options: ['GET', 'POST', 'PUT', 'DELETE'],
      systemId: null,
      systemName: null,
      menuId: null,
      menuName: null
    }
  },
  methods: {
    show (data) {
      var self = this
      if (this.$refs.moduleAddForm) {
        // 重置表单
        self.$refs.moduleAddForm.resetFields()
      }
      this.form.menuCode = null
      this.form.menuName = null
      this.form.menuUrl = null
      this.form.parentId = data.menuId
      this.form.imgSrc = null
      this.form.isLeaf = 0
      this.form.sortNo = 0
      this.form.systemId = data.systemId
      this.form.status = 1
      this.addModuleShow = true

      this.systemId = data.systemId
      this.systemName = data.systemName
      this.menuId = data.menuId
      this.menuName = data.menuName
    },
    saveModule () {
      var self = this
      // 校验表单
      self.$refs.moduleAddForm.validate(result => {
        self.addModuleLoading = true
        if (result) {
          self.$http.post(`${DataMainApi}/menu/add`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success(self.$t('constant.module.SAVE_MODULE_SUCCESS_NOTIFY'))
                self.addModuleShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error(self.$t('constant.module.SAVE_MODULE_FAILED_NOTIFY'))
              }
              self.addModuleLoading = false
            })
            .catch(() => {
              self.addModuleLoading = false
            })
        } else {
          self.addModuleLoading = false
        }
      })
    }
  }
}
</script>
