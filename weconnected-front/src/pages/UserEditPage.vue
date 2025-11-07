<template>
  <van-form @submit="onSubmit">
      <!-- 性别选择器 -->
      <template v-if="editUser.editKey === 'gender'">
        <van-cell-group inset>
          <van-cell title="性别" />
          <van-radio-group v-model="genderValue" direction="horizontal" style="padding: 16px;">
            <van-radio :name="0">男</van-radio>
            <van-radio :name="1">女</van-radio>
          </van-radio-group>
        </van-cell-group>
      </template>
      <!-- 其他字段使用原来的输入框 -->
      <template v-else>
        <van-field
            v-model="editUser.currentValue"
            :name="editUser.editKey"
            :label="editUser.editName"
            :placeholder="`请输入${editUser.editName}`"
        />
      </template>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>
</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import { ref } from "vue";
import myAxios from "../plugins/myAxios";
import {Toast} from "vant";
import {getCurrentUser} from "../services/user";

const route = useRoute();
const router = useRouter();

const editUser = ref({
  editKey: route.query.editKey,
  currentValue: route.query.currentValue,
  // editName: route.query.editName,
  editName: route.query.editName ?? '内容',
})

// 性别选择器的值
const genderValue = ref<number>(
  editUser.value.editKey === 'gender' 
    ? (typeof editUser.value.currentValue === 'string' 
        ? parseInt(editUser.value.currentValue) 
        : editUser.value.currentValue)
    : 0
);

const onSubmit = async () => {
  const currentUser = await getCurrentUser();

  if (!currentUser) {
    Toast.fail('用户未登录');
    return;
  }

  console.log(currentUser, '当前用户')

  // 如果是性别字段，使用选择器的值，否则使用输入框的值
  const updateValue = editUser.value.editKey === 'gender' 
    ? genderValue.value 
    : editUser.value.currentValue;

  const res = await myAxios.post('/user/update', {
    'id': currentUser.id,
    [editUser.value.editKey as string]: updateValue,
  })
  console.log(res, '更新请求');
  if (res.code === 0 && res.data > 0) {
    Toast.success('修改成功');
    router.back();
  } else {
    Toast.fail('修改错误');
  }
};

</script>

<style scoped>

</style>
