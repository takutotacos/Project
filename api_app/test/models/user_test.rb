require 'test_helper'

class UserTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
  def setup
    @user = User.new(user_id: "Rambo", email: "sugita001@gmail.com", password: "1234", password_confirmation: "1234")
  end

  test "associated postings should be destroyed" do
    @user.save
    @user.postings.create!(comment: "Nothing is over!!!")
    assert_difference "Posting.count", -1 do
      @user.destroy
    end
  end

  test "should follow and unfollow a user" do
  	michael = users(:michael)
  	archer = users(:archer)
  	assert_not michael.following?(archer)
  	michael.follow(archer)
  	assert michael.following?(archer)
      assert archer.followers.include?(michael)
  	michael.unfollow(archer)
  	assert_not michael.following?(archer)
  end
end
