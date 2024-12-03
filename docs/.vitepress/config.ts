import { defineConfig } from 'vitepress'

import { enUS } from './locales/en-US'
import { zhCN } from './locales/zh-CN'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: 'Tauri Plugin Board',
  description: 'Tauri Plugin Board',

  lastUpdated: true,
  cleanUrls: true,
  metaChunk: true,

  markdown: {
    math: true,
    codeTransformers: [
      // We use `[!!code` in demo to prevent transformation, here we revert it back.
      {
        postprocess(code) {
          return code.replace(/\[\!\!code/g, '[!code')
        }
      }
    ]
  },

  sitemap: {
    hostname: 'https://www.cakioe.com/docs',
    transformItems(items) {
      return items.filter(item => !item.url.includes('migration'))
    }
  },

  /* prettier-ignore */
  head: [
    ['meta', { name: 'theme-color', content: '#5f67ee' }],
    ['meta', { property: 'og:type', content: 'website' }],
    ['meta', { property: 'og:locale', content: 'en' }],
    ['meta', { property: 'og:title', content: 'Tauri Plugin Board | ' }],
    ['meta', { property: 'og:site_name', content: 'Tauri Plugin Board' }],
    ['meta', { property: 'og:url', content: 'https://www.cakioe.com/' }]
  ],

  base: '/docs/',
  appearance: 'dark',

  locales: {
    root: { label: '简体中文', ...zhCN },
    'en-US': { label: 'English', ...enUS }
  }
})
