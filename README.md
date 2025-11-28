# Yakka Dee Maths

本项目是一个面向幼儿的加减乘除练习网页，连续答对5题可解锁观看《Yakka Dee》剧集，并支持家长奖励、难度与类型选择。

## 运行

- 直接双击 `yakka_dee_maths_v1.html` 或在本地服务器下打开
- 素材文件需与页面同目录：
  - `yakke_dee_figure1.jpg`、`yakke_dee_figure2.jpg`、`yakke_dee_figure3.jpg`
  - `yakka_dee_opening.mp4`、`yakka_kee_ending.mp4`

## 发布到 GitHub Pages

- 将仓库默认分支设为 `main`
- 在 Settings → Pages 中选择 `Deploy from a branch`，Branch 选 `main`，Folder 选 `/(root)`
- 首页使用 `index.html` 跳转到 `yakka_dee_maths_v1.html`

已配置 GitHub Actions 自动部署，推送到 `main` 分支后会自动发布。

访问地址（部署完成后）：

`https://codingzed.github.io/yakka_dee_maths_learning/`

## 可配置项

- 连胜解锁数：`targetWins`
- 观看计时：`EPISODE_MS`
- 季与集：`seasons` 数组（`bvid` 与 `episodes`）

## 许可

素材与外部视频链接仅用于学习与演示，请根据实际版权政策合理使用。
