import typescript from '@rollup/plugin-typescript'
import terser from '@rollup/plugin-terser'
import { dts } from 'rollup-plugin-dts'

const config = [{
  input: 'webview-src/index.ts',
  plugins: [
    typescript({
      include: ['webview-src/**/*.ts', 'webview-src/**/*.tsx'],
      tsconfig: 'tsconfig.json'
    }),
    terser()
  ],
  output: [{
    file: 'webview-dist/index.mjs',
    format: 'es',
    sourcemap: true
  }, {
    file: 'webview-dist/index.cjs',
    format: 'cjs',
    sourcemap: true
  }]
}, {
  input: './webview-src/index.d.ts',
  output: [{ file: 'webview-dist/index.d.ts', format: 'es' }],
  plugins: [dts()]
}]

export default config
