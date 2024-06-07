import typescript from '@rollup/plugin-typescript'

export default {
  input: 'webview-src/index.ts',
  plugins: [typescript({
    include: ['webview-src/**/*.ts', 'webview-src/**/*.tsx']
  })],
  output: [{
    file: 'webview-dist/index.js',
    format: 'es'
  }, {
    file: 'webview-dist/index.cjs',
    format: 'cjs'
  }]
}
