// 로그인 폼 제출 시 실행되는 함수
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();  // 폼의 기본 제출 동작을 막음

    // 사용자가 입력한 이메일과 비밀번호를 가져옴
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // 서버로 로그인 요청을 보냄
    fetch('/jwtLogin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: email, password: password })
    })
        .then(response => response.text())
        .then(data => {
            if (data) {
                // 로그인 성공 시, 받은 JWT 토큰을 로컬 스토리지에 저장
                localStorage.setItem('jwtToken', data);
                alert("로그인 성공");
                // 로그인 후 홈 페이지로 리디렉션
                window.location.href = "/home";
            } else {
                alert("로그인 실패");
            }
        })
        .catch(error => {
            console.error('로그인 오류:', error);
            alert("로그인 실패");
        });
});
