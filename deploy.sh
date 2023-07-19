# spring app 실행
cd ~/todo-max/be                                                      # be 디렉토리로 이동
sudo chmod +x ./gradlew                                               # gradlew에 실행 권한 부여
./gradlew clean build -x test                                         # 테스트 없이 빌드
cd build/libs/                                                        # build/libs/ 디렉토리로 이동
nohup java -Dspring.profiles.active=prod -jar *.jar 1>~/log.out 2>~/err.out & # prod 프로파일로 스프링 실행,
# 표준 출력 로그는 ~/log.out에, 에러 출력 로그는 ~/err.out에 저장, 백그라운드로 실행

# react web 실행
cd ~/todo-max/frontend
npm install                               # node_modules
npm run build                             # 리액트 빌드
npm install -g serve                      # serve 설치
nohup serve -s build 1>~/fe-log.out 2>~/fe-err.out & # 백그라운드 실행
