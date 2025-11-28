<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Yakka Dee Math Time!</title>
    <style>
        body {
            font-family: 'Comic Sans MS', 'Chalkboard SE', sans-serif;
            background-color: #f0f8ff;
            text-align: center;
            margin: 0;
            padding: 20px;
            user-select: none; /* 防止长按选中文字 */
        }
        h1 { color: #ff6b6b; }
        
        /* 进度条星星 */
        #stars { font-size: 40px; margin-bottom: 20px; }
        .star-on { color: gold; }
        .star-off { color: #ddd; }

        /* 题目区域 */
        #question-box {
            background: white;
            border-radius: 20px;
            padding: 30px;
            margin: 10px auto;
            max-width: 500px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        #visual-aid {
            font-size: 50px;
            letter-spacing: 5px;
            margin-bottom: 10px;
        }
        #equation {
            font-size: 60px;
            color: #333;
            font-weight: bold;
        }

        /* 答案按钮区 */
        #answers {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 30px;
        }
        .btn {
            background-color: #4ecdc4;
            color: white;
            border: none;
            border-radius: 50%;
            width: 80px;
            height: 80px;
            font-size: 35px;
            cursor: pointer;
            box-shadow: 0 4px #2a9d8f;
            transition: transform 0.1s;
        }
        .btn:active {
            transform: translateY(4px);
            box-shadow: none;
        }
        .btn-wrong { background-color: #ff6b6b; box-shadow: 0 4px #c44d4d; }

        /* 视频区域 */
        #video-container {
            display: none; /* 默认隐藏 */
            margin-top: 20px;
        }
        iframe {
            width: 100%;
            max-width: 560px;
            height: 315px;
            border-radius: 15px;
        }
        #next-round-btn {
            display: none;
            margin-top: 20px;
            background-color: #ff9f43;
            width: 200px;
            border-radius: 50px;
        }

    </style>
</head>
<body>

    <!-- 顶部标题 -->
    <h1>⭐ Yakka Dee 学数学 ⭐</h1>

    <!-- 游戏区域 -->
    <div id="game-area">
        <div id="stars">
            <span id="s1" class="star-off">★</span>
            <span id="s2" class="star-off">★</span>
            <span id="s3" class="star-off">★</span>
        </div>

        <div id="question-box">
            <div id="visual-aid">🍎🍎 + 🍎</div> <!-- 视觉辅助 -->
            <div id="equation">2 + 1 = ?</div>
        </div>

        <div id="answers">
            <!-- 按钮由JS生成 -->
        </div>
    </div>

    <!-- 奖励区域 -->
    <div id="video-container">
        <h2 style="color: purple">做得好！来看 Yakka Dee 吧！</h2>
        <!-- 这里嵌入的是YouTube视频，您可以更换src链接 -->
        <iframe id="video-player" src="" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
        <br>
        <button class="btn" id="next-round-btn" onclick="resetGame()">继续学习</button>
    </div>

    <script>
        // --- 配置区 ---
        const targetWins = 3; // 答对几道题解锁视频
        const maxNumber = 10; // 最大数字
        // Yakka Dee 的 YouTube 视频 ID 列表 (您可以添加更多)
        const videoIds = [
            "ptStl2-2kXw", // Yakka Dee - Banana
            "Jp2yU_qH6v0", // Yakka Dee - Dog
            "8Z2zW4o6vFk"  // Yakka Dee - Apple
        ];
        
        // --- 变量 ---
        let currentWins = 0;
        let currentCorrectAnswer = 0;

        // --- 初始化 ---
        window.onload = function() {
            generateQuestion();
        };

        // --- 生成题目逻辑 ---
        function generateQuestion() {
            // 简单起见，这里主要生成加法，少量减法 (适合3岁)
            // 如果需要乘除，可以扩展
            let isAddition = Math.random() > 0.3; // 70%概率加法
            let num1, num2;

            if (isAddition) {
                num1 = Math.floor(Math.random() * 6); // 0-5
                num2 = Math.floor(Math.random() * (maxNumber - num1)); 
                currentCorrectAnswer = num1 + num2;
                document.getElementById('equation').innerText = `${num1} + ${num2} = ?`;
                document.getElementById('visual-aid').innerText = "🍎".repeat(num1) + " ➕ " + "🍎".repeat(num2);
            } else {
                // 减法：确保结果不为负数
                num1 = Math.floor(Math.random() * 6) + 1; // 1-6
                num2 = Math.floor(Math.random() * (num1 + 1));
                currentCorrectAnswer = num1 - num2;
                document.getElementById('equation').innerText = `${num1} - ${num2} = ?`;
                document.getElementById('visual-aid').innerText = "🍎".repeat(num1) + " ➖ " + "🍎".repeat(num2);
            }

            generateButtons(currentCorrectAnswer);
        }

        // --- 生成选项按钮 ---
        function generateButtons(correct) {
            let answersContainer = document.getElementById('answers');
            answersContainer.innerHTML = '';

            // 生成两个错误答案
            let wrong1 = correct + Math.floor(Math.random() * 3) + 1;
            let wrong2 = Math.max(0, correct - Math.floor(Math.random() * 3) - 1);
            
            // 防止答案重复
            let options = [correct, wrong1, wrong2];
            options = [...new Set(options)]; // 去重
            while(options.length < 3) {
                options.push(options[options.length-1] + 1);
            }
            
            // 打乱顺序
            options.sort(() => Math.random() - 0.5);

            options.forEach(num => {
                let btn = document.createElement('button');
                btn.className = 'btn';
                btn.innerText = num;
                btn.onclick = () => checkAnswer(num, btn);
                answersContainer.appendChild(btn);
            });
        }

        // --- 检查答案 ---
        function checkAnswer(selected, btnElement) {
            if (selected === currentCorrectAnswer) {
                // 答对了
                currentWins++;
                updateStars();
                
                if (currentWins >= targetWins) {
                    showReward();
                } else {
                    // 稍微延迟后下一题
                    setTimeout(generateQuestion, 500);
                }
            } else {
                // 答错了
                btnElement.classList.add('btn-wrong');
                // 摇晃效果可以在CSS加，这里简化处理
            }
        }

        // --- 更新星星 ---
        function updateStars() {
            for(let i=1; i<=3; i++) {
                let star = document.getElementById('s' + i);
                if (i <= currentWins) {
                    star.className = 'star-on';
                } else {
                    star.className = 'star-off';
                }
            }
        }

        // --- 显示奖励 ---
        function showReward() {
            document.getElementById('game-area').style.display = 'none';
            document.getElementById('video-container').style.display = 'block';
            document.getElementById('next-round-btn').style.display = 'inline-block';

            // 随机选一个视频
            let randomVid = videoIds[Math.floor(Math.random() * videoIds.length)];
            let embedUrl = `https://www.youtube.com/embed/${randomVid}?autoplay=1`;
            document.getElementById('video-player').src = embedUrl;
        }

        // --- 重置游戏 ---
        function resetGame() {
            currentWins = 0;
            updateStars();
            document.getElementById('video-player').src = ""; // 停止视频
            document.getElementById('video-container').style.display = 'none';
            document.getElementById('game-area').style.display = 'block';
            generateQuestion();
        }
    </script>
</body>
</html>