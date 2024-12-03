import { defineConfig, type DefaultTheme } from 'vitepress'

// [document](https://devonline.net/technology/learn/esm-require.html)
import * as app from '../../../package.json'

const Nav: DefaultTheme.NavItem[] = [
  { text: '首页', link: '/' },
  { text: '指南', link: '/guide' },
]

const SidebarGuide: DefaultTheme.SidebarItem[] = [
  {
    text: 'Guide',
    collapsed: false,
    items: [
      { text: 'Getting Started', link: 'guide' }
    ]
  }
]

export const zhCN = defineConfig({
  lang: 'zh-Hans',
  description: 'Tauri Plugin Board',
  head: [['link', { rel: 'icon', type: 'image/png', sizes: '32x32', href: './public/icon.png' }]],
  themeConfig: {
    nav: Nav,
    logo: './public/icon.png',
    siteTitle: 'Cakioe',
    sidebar: {
      '/': { base: '/', items: SidebarGuide }
    },

    footer: {
      message: '© All rights reserved. Tauri Plugin Board',
      copyright: `Powered cakioe.com. 2014 - ${new Date().getFullYear()} v${app.version}`
    },

    docFooter: {
      prev: '上一页',
      next: '下一页'
    },

    outline: {
      label: '页面导航'
    },

    lastUpdated: {
      text: '最后更新于',
      formatOptions: {
        dateStyle: 'short',
        timeStyle: 'medium'
      }
    },

    langMenuLabel: '多语言',
    returnToTopLabel: '回到顶部',
    sidebarMenuLabel: '菜单',
    darkModeSwitchLabel: '主题',
    lightModeSwitchTitle: '切换到浅色模式',
    darkModeSwitchTitle: '切换到深色模式'
  }
})

/**
 * @description: 简体中文
 */
export const search: DefaultTheme.AlgoliaSearchOptions['locales'] = {
  zh: {
    placeholder: '搜索文档',
    translations: {
      button: {
        buttonText: '搜索文档',
        buttonAriaLabel: '搜索文档'
      },
      modal: {
        searchBox: {
          resetButtonTitle: '清除查询条件',
          resetButtonAriaLabel: '清除查询条件',
          cancelButtonText: '取消',
          cancelButtonAriaLabel: '取消'
        },
        startScreen: {
          recentSearchesTitle: '搜索历史',
          noRecentSearchesText: '没有搜索历史',
          saveRecentSearchButtonTitle: '保存至搜索历史',
          removeRecentSearchButtonTitle: '从搜索历史中移除',
          favoriteSearchesTitle: '收藏',
          removeFavoriteSearchButtonTitle: '从收藏中移除'
        },
        errorScreen: {
          titleText: '无法获取结果',
          helpText: '你可能需要检查你的网络连接'
        },
        footer: {
          selectText: '选择',
          navigateText: '切换',
          closeText: '关闭',
          searchByText: '搜索提供者'
        },
        noResultsScreen: {
          noResultsText: '无法找到相关结果',
          suggestedQueryText: '你可以尝试查询',
          reportMissingResultsText: '你认为该查询应该有结果？',
          reportMissingResultsLinkText: '点击反馈'
        }
      }
    }
  }
}
