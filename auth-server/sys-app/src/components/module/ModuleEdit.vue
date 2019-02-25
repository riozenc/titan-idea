<template lang="html">
  <el-dialog :title="$t('constant.module.EDIT_MODULE')" :visible.sync="editModuleShow">
    <el-form ref="moduleEditForm" :model="form" label-width="160px" :rules="formRules" :inline="true">
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
            <el-form-item :label="$t('constant.module.MODULE_CODE')">
              <el-input v-model="form.menuCode" :disabled="true"></el-input>
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
          <el-col :span="12" v-show="form.isOperating">
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
      <el-button @click="editModuleShow = false">{{$t('button.CANCEL')}}</el-button>
      <el-button type="primary" @click="saveModule" :loading="editModuleLoading">{{$t('button.SURE')}}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {DataMainApi, Status} from '../ApiConstant'
export default {
  data () {
    var self = this
    return {
      form: {
        id: null,
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
        menuName: [
          { required: true, message: self.$t('constant.module.MODULE_NAME_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 32, message: self.$t('constant.module.THREE_TO_32'), trigger: 'blur' }
        ],
        menuUrl: [
          { required: true, message: self.$t('constant.module.MODULE_PATH_PLACEHOLDER'), trigger: 'blur' },
          { min: 3, max: 100, message: self.$t('constant.module.THREE_TO_100'), trigger: 'blur' }
        ]
      },
      editModuleShow: false,
      editModuleLoading: false,
      options: ['GET', 'POST', 'PUT', 'DELETE'],
      systemId: null,
      systemName: null,
      moduleId: null,
      moduleName: null
    }
  },
  methods: {
    show (row, data) {
      var self = this
      if (this.$refs.moduleEditForm) {
        // 重置表单
        self.$refs.moduleEditForm.resetFields()
      }
      this.form.id = row.id
      this.form.moduleCode = row.moduleCode
      this.form.moduleName = row.moduleName
      this.form.modulePath = row.modulePath
      this.form.parentId = row.parentId
      this.form.moduleIcon = row.moduleIcon
      this.form.httpMethod = row.httpMethod
      this.form.isOperating = row.isOperating
      this.form.sort = row.sort
      this.form.systemId = row.systemId
      this.form.active = row.active

      this.systemId = data.systemId
      this.systemName = data.systemName
      this.moduleId = data.moduleId
      this.moduleName = data.moduleName
      this.editModuleShow = true
    },
    saveModule () {
      var self = this
      // 校验表单
      self.$refs.moduleEditForm.validate(result => {
        self.editModuleLoading = true
        if (result) {
          self.$http.post(`${DataMainApi}/menu/update`, self.form)
            .then(res => {
              if (res.data.code === Status.success) {
                self.$notify.success(self.$t('constant.module.SAVE_MODULE_SUCCESS_NOTIFY'))
                self.editModuleShow = false
                // 触发事件
                self.$emit('success')
              } else {
                self.$notify.error(self.$t('constant.module.SAVE_MODULE_FAILED_NOTIFY'))
              }
              self.editModuleLoading = false
            })
            .catch(() => {
              self.editModuleLoading = false
            })
        } else {
          self.editModuleLoading = false
        }
      })
    }
  }
}
</script>
