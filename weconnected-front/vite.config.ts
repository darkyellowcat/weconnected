import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import styleImport, {VantResolve} from 'vite-plugin-style-import';

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue(), styleImport({
        resolves: [VantResolve()],
    }),],
    server: {
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
            }
        }
    },
    // 修复 sockjs-client 的兼容性问题
    define: {
        'global': 'globalThis',
        'process.env': {},
    },
    resolve: {
        alias: {
            // 为 Node.js 模块提供浏览器 polyfill
            util: 'util/',
        },
    },
    optimizeDeps: {
        include: ['util'],
    },
})
