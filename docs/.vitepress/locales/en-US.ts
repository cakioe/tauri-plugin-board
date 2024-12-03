import { defineConfig, type DefaultTheme } from 'vitepress'

// [document](https://devonline.net/technology/learn/esm-require.html)
import * as app from '../../../package.json'

const Nav: DefaultTheme.NavItem[] = [
  { text: 'Home', link: '/en-US/' },
  { text: 'Guide', link: '/en-US/guide' },
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

export const enUS = defineConfig({
  title: 'Cakioe',
  lang: 'en-US',
  description: '',
  head: [['link', { rel: 'icon', type: 'image/png', sizes: '32x32', href: '../public/icon.png' }]],
  themeConfig: {
    nav: Nav,
    logo: '../public/icon.png',
    sidebar: {
      '/en-US/': { base: '/en-US/', items: SidebarGuide }
    },

    footer: {
      message: '© All rights reserved. Tauri Plugin Board',
      copyright: `Powered cakioe.com. 2014 - ${new Date().getFullYear()} v${app.version}`
    }
  }
})

/**
 * @description: 英文
 */
export const search: DefaultTheme.AlgoliaSearchOptions['locales'] = {
  en: {
    placeholder: 'Search Documentation',
    translations: {
      button: {
        buttonText: 'Search Documentation',
        buttonAriaLabel: 'Search Documentation'
      },
      modal: {
        searchBox: {
          resetButtonTitle: 'Clear query',
          resetButtonAriaLabel: 'Clear query',
          cancelButtonText: 'Cancel',
          cancelButtonAriaLabel: 'Cancel'
        },
        startScreen: {
          recentSearchesTitle: 'Recent searches',
          noRecentSearchesText: 'No recent searches',
          saveRecentSearchButtonTitle: 'Save this search',
          removeRecentSearchButtonTitle: 'Remove this search from history',
          favoriteSearchesTitle: 'Favorite searches',
          removeFavoriteSearchButtonTitle: 'Remove this search from favorites'
        },
        errorScreen: {
          titleText: 'No results',
          helpText: 'You might need to check your network connection'
        },
        footer: {
          selectText: 'Select',
          navigateText: 'Go',
          closeText: 'Close',
          searchByText: 'Search by'
        },
        noResultsScreen: {
          noResultsText: 'No results found for query',
          suggestedQueryText: 'Try querying for',
          reportMissingResultsText: 'Report this missing result',
          reportMissingResultsLinkText: 'Learn more'
        }
      }
    }
  }
}
